package com.terratrue.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.google.appengine.logging.v1.LogLine;
import com.google.appengine.logging.v1.RequestLog;
import com.google.appengine.logging.v1.SourceLocation;
import com.google.cloud.MonitoredResource;
import com.google.cloud.ServiceOptions;
import com.google.cloud.logging.LogEntry;
import com.google.cloud.logging.Logging;
import com.google.cloud.logging.Logging.WriteOption;
import com.google.cloud.logging.LoggingEnhancer;
import com.google.cloud.logging.LoggingOptions;
import com.google.cloud.logging.MonitoredResourceUtil;
import com.google.cloud.logging.Payload.ProtoPayload;
import com.google.cloud.logging.Severity;
import com.google.cloud.logging.Synchronicity;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.logging.type.LogSeverity;
import com.google.protobuf.Any;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.slf4j.MDC;

/*
 * Adapted from Google's LoggingAppender 0.122.9-alpha
 * https://github.com/googleapis/java-logging-logback
 */
public class CustomLoggingAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
  private static final int LOG_LINES_LIMIT = 50;

  private Level flushLevel;
  private String log;
  private Synchronicity writeSyncFlag = Synchronicity.ASYNC;

  // Accumulate log entries per trace id, which is unique per http request.
  // Any access to this HashMap must be protected by synchronized keyword
  private HashMap<String, List<LogLine>> traceId2LogLines = new HashMap<>();
  private volatile Logging logging;
  private LoggingOptions loggingOptions;
  private List<LoggingEnhancer> loggingEnhancers;
  private WriteOption[] defaultWriteOptions;

  // Options
  public void setFlushLevel(Level flushLevel) {
    this.flushLevel = flushLevel;
  }

  public void setLog(String log) {
    this.log = log;
  }

  Level getFlushLevel() {
    return (flushLevel != null) ? flushLevel : Level.ERROR;
  }

  String getLogName() {
    return (log != null) ? log : "java.log";
  }

  @Override
  public synchronized void start() {
    if (isStarted()) {
      return;
    }
    MonitoredResource resource = MonitoredResourceUtil.getResource(getProjectId(), null);
    defaultWriteOptions =
        new WriteOption[] {WriteOption.logName(getLogName()), WriteOption.resource(resource)};
    Level flushLevel = getFlushLevel();
    if (flushLevel != Level.OFF) {
      getLogging().setFlushSeverity(severityFor(flushLevel));
    }
    loggingEnhancers = new ArrayList<>();
    List<LoggingEnhancer> resourceEnhancers = MonitoredResourceUtil.getResourceEnhancers();
    loggingEnhancers.addAll(resourceEnhancers);

    super.start();
  }

  private String getProjectId() {
    return getLoggingOptions().getProjectId();
  }

  @Override
  public synchronized void stop() {
    if (logging != null) {
      try {
        logging.close();
      } catch (Exception ex) {
        // ignore
      }
    }
    logging = null;
    super.stop();
  }

  @Override
  protected void append(ILoggingEvent e) {
    // If there is no traceId, we drop the log
    final String traceId = MDC.get(Constants.MDC_KEY_TRACE_ID);
    if (Strings.isNullOrEmpty(traceId)) {
      return;
    }

    final LogLine logLine = toLogLine(e);
    final int currentSize = addLogLine(traceId, logLine);

    if (currentSize >= LOG_LINES_LIMIT) {
      List<LogLine> copy = swapOut(traceId, LOG_LINES_LIMIT, false);
      if (copy != null) {
        writeOut(copy);
      }
    }
  }

  private LogLine toLogLine(ILoggingEvent e) {
    final Instant instant = Instant.ofEpochMilli(e.getTimeStamp());
    Timestamp timestamp =
        Timestamp.newBuilder()
            .setSeconds(instant.getEpochSecond())
            .setNanos(instant.getNano())
            .build();

    StackTraceElement stackTraceElement = e.getCallerData()[0];

    LogLine logLine =
        LogLine.newBuilder()
            .setLogMessage(getMessage(e))
            .setSeverity(logSeverityFor(e.getLevel()))
            .setSourceLocation(
                SourceLocation.newBuilder()
                    .setFile(stackTraceElement.getFileName())
                    .setFunctionName(
                        stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName())
                    .setLine(stackTraceElement.getLineNumber())
                    .build())
            .setTime(timestamp)
            .build();
    return logLine;
  }

  private String getMessage(final ILoggingEvent e) {
    if (e.getThrowableProxy() == null) {
      return e.getFormattedMessage();
    }

    StringBuilder msg = new StringBuilder(e.getFormattedMessage()).append('\n');
    writeStack(e.getThrowableProxy(), "", msg);
    return msg.toString();
  }

  private static void writeStack(IThrowableProxy throwProxy, String prefix, StringBuilder payload) {
    if (throwProxy == null) {
      return;
    }
    payload
        .append(prefix)
        .append(throwProxy.getClassName())
        .append(": ")
        .append(throwProxy.getMessage())
        .append('\n');
    StackTraceElementProxy[] trace = throwProxy.getStackTraceElementProxyArray();
    if (trace == null) {
      trace = new StackTraceElementProxy[0];
    }

    int commonFrames = throwProxy.getCommonFrames();
    int printFrames = trace.length - commonFrames;
    for (int i = 0; i < printFrames; i++) {
      payload.append("    ").append(trace[i]).append('\n');
    }
    if (commonFrames != 0) {
      payload.append("    ... ").append(commonFrames).append(" common frames elided\n");
    }

    writeStack(throwProxy.getCause(), "caused by: ", payload);
  }

  // Add a LogLine in a thread-safe way
  private synchronized int addLogLine(final String traceId, final LogLine logLine) {
    if (!traceId2LogLines.containsKey(traceId)) {
      traceId2LogLines.put(traceId, Lists.newArrayList());
    }
    List<LogLine> logLines = traceId2LogLines.get(traceId);
    logLines.add(logLine);
    return logLines.size();
  }

  // Return a copy of the log lines for a specific traceId if it has at least min lines
  // This method is thread-safe
  private synchronized List<LogLine> swapOut(
      final String traceId, final int min, final boolean removeTraceIdEntry) {
    final List<LogLine> logLines = traceId2LogLines.get(traceId);
    if (logLines == null) {
      return null;
    }

    if (logLines.size() < min) {
      if (removeTraceIdEntry) {
        traceId2LogLines.remove(traceId);
      }
      return null;
    }

    List<LogLine> copy = Lists.newArrayList(logLines);
    logLines.clear();
    if (removeTraceIdEntry) {
      traceId2LogLines.remove(traceId);
    }
    return copy;
  }

  public void finalWrite() {
    // Return if there is no traceId
    final String traceId = MDC.get(Constants.MDC_KEY_TRACE_ID);
    if (Strings.isNullOrEmpty(traceId)) {
      return;
    }

    List<LogLine> copy = swapOut(traceId, 1, true);
    if (copy != null) {
      writeOut(copy);
    }
  }

  private void writeOut(List<LogLine> copy) {
    RequestLog.Builder requestLogBuilder =
        RequestLog.getDefaultInstance().toBuilder()
            .setAppId(ServiceOptions.getDefaultProjectId())
            .setHost(MDC.get(Constants.MDC_KEY_HOST))
            .setHttpVersion(MDC.get(Constants.MDC_KEY_HTTP_VERSION))
            .setMethod(MDC.get(Constants.MDC_KEY_METHOD))
            .setResource(MDC.get(Constants.MDC_KEY_RESOURCE))
            .setTraceId(MDC.get(Constants.MDC_KEY_TRACE_ID))
            .addAllLine(copy);

    String instanceId = System.getenv(Constants.SYSTEM_ENV_NAME_GAE_INSTANCE);
    if (null != instanceId) {
      requestLogBuilder.setInstanceId(instanceId);
    }
    String userAgent = MDC.get(Constants.MDC_KEY_USER_AGENT);
    if (null != userAgent) {
      requestLogBuilder.setUserAgent(userAgent);
    }
    String ip = MDC.get(Constants.MDC_KEY_IP);
    if (null != ip) {
      requestLogBuilder.setIp(ip);
    }
    String referrer = MDC.get(Constants.MDC_KEY_REFERRER);
    if (null != referrer) {
      requestLogBuilder.setReferrer(referrer);
    }
    String responseStatusString = MDC.get(Constants.MDC_KEY_STATUS);
    if (null != responseStatusString) {
      requestLogBuilder.setStatus(Integer.parseInt(responseStatusString));
    }

    RequestLog requestLog = requestLogBuilder.build();

    LogEntry.Builder logEntryBuilder =
        LogEntry.newBuilder(ProtoPayload.of(Any.pack(requestLog)))
            .setSeverity(getHighestSeverity(copy))
            .setResource(
                MonitoredResourceUtil.getResource(
                    ServiceOptions.getDefaultProjectId(), Constants.APP_ENGINE_RESOURCE_NAME))
            .setLogName(Constants.APP_ENGINE_REQUEST_LOG_NAME)
            .setTrace(MDC.get(Constants.MDC_KEY_TRACE))
            .setTimestamp(
                Instant.ofEpochMilli(
                    copy.get(0).getTime().getSeconds() * 1000
                        + copy.get(0).getTime().getNanos() / 1000000));

    if (loggingEnhancers != null) {
      for (LoggingEnhancer enhancer : loggingEnhancers) {
        enhancer.enhanceLogEntry(logEntryBuilder);
      }
    }
    getLogging().write(Collections.singleton(logEntryBuilder.build()), defaultWriteOptions);
  }

  private Logging getLogging() {
    if (logging == null) {
      synchronized (this) {
        if (logging == null) {
          logging = getLoggingOptions().getService();
          logging.setWriteSynchronicity(writeSyncFlag);
        }
      }
    }
    return logging;
  }

  private LoggingOptions getLoggingOptions() {
    if (loggingOptions == null) {
      loggingOptions = LoggingOptions.getDefaultInstance();
    }
    return loggingOptions;
  }

  private Severity getHighestSeverity(final List<LogLine> copy) {
    final LogSeverity highestLogSeverity =
        LogSeverity.forNumber(
            copy.stream()
                .map(logLine -> logLine.getSeverity().getNumber())
                .max(Integer::compare)
                .get());

    return severityFor(highestLogSeverity);
  }

  private static LogSeverity logSeverityFor(Level level) {
    switch (level.toInt()) {
        // TRACE
      case 5000:
        return LogSeverity.DEBUG;
        // DEBUG
      case 10000:
        return LogSeverity.DEBUG;
        // INFO
      case 20000:
        return LogSeverity.INFO;
        // WARNING
      case 30000:
        return LogSeverity.WARNING;
        // ERROR
      case 40000:
        return LogSeverity.ERROR;
      default:
        return LogSeverity.DEFAULT;
    }
  }

  private static Severity severityFor(Level level) {
    switch (level.toInt()) {
        // TRACE
      case 5000:
        return Severity.DEBUG;
        // DEBUG
      case 10000:
        return Severity.DEBUG;
        // INFO
      case 20000:
        return Severity.INFO;
        // WARNING
      case 30000:
        return Severity.WARNING;
        // ERROR
      case 40000:
        return Severity.ERROR;
      default:
        return Severity.DEFAULT;
    }
  }

  private static Severity severityFor(LogSeverity logSeverity) {
    switch (logSeverity) {
      case DEBUG:
        return Severity.DEBUG;
      case INFO:
        return Severity.INFO;
      case NOTICE:
        return Severity.NOTICE;
      case WARNING:
        return Severity.WARNING;
      case ERROR:
        return Severity.ERROR;
      case CRITICAL:
        return Severity.CRITICAL;
      case ALERT:
        return Severity.ALERT;
      case EMERGENCY:
        return Severity.EMERGENCY;
      default:
        return Severity.DEFAULT;
    }
  }
}
