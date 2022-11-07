package com.terratrue.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.google.cloud.ServiceOptions;
import com.google.common.base.Strings;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;

@WebFilter(filterName = "TraceFilter", urlPatterns = "/*")
public class TraceFilter implements Filter {
  java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TraceFilter.class.getName());
  private CustomLoggingAppender customLoggingAppender;

  @Override
  public void init(FilterConfig filterConfig) {
    logger.info(String.format("%s has started.", this.getClass().getName()));
    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

    for (Logger logBackLogger : loggerContext.getLoggerList()) {
      Iterator<Appender<ILoggingEvent>> appenderIterator = logBackLogger.iteratorForAppenders();
      while (appenderIterator.hasNext()) {
        Appender<ILoggingEvent> appender = appenderIterator.next();
        if ("CUSTOM_LOGGING_APPENDER".equalsIgnoreCase(appender.getName())) {
          logger.info("Found the CUSTOM_LOGGING_APPENDER.");
          customLoggingAppender = (CustomLoggingAppender) appender;
        }
      }
    }
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String resource = httpServletRequest.getRequestURI();
    if (!Strings.isNullOrEmpty(httpServletRequest.getQueryString())) {
      resource += "?" + httpServletRequest.getQueryString();
    }

    final String traceContext =
        httpServletRequest.getHeader(Constants.APP_ENGINE_HEADER_NAME_CLOUD_TRACE_CONTEXT);
    if (!Strings.isNullOrEmpty(traceContext)) {
      String traceId = traceContext.split("/")[0];
      String trace =
          String.format(
              Constants.APP_ENGINE_TRACE_FORMAT_STR, ServiceOptions.getDefaultProjectId(), traceId);
      MDC.put(Constants.MDC_KEY_TRACE, trace);
      MDC.put(Constants.MDC_KEY_TRACE_ID, traceId);
    }
    MDC.put(Constants.MDC_KEY_HOST, request.getServerName());
    MDC.put(Constants.MDC_KEY_HTTP_VERSION, request.getProtocol());
    MDC.put(Constants.MDC_KEY_METHOD, httpServletRequest.getMethod());
    MDC.put(Constants.MDC_KEY_RESOURCE, resource);
    String userAgent = httpServletRequest.getHeader(HttpHeaders.USER_AGENT);
    if (null != userAgent) {
      MDC.put(Constants.MDC_KEY_USER_AGENT, userAgent);
    }
    String forwardedFor =
        httpServletRequest.getHeader(Constants.APP_ENGINE_HEADER_NAME_FORWARDED_FOR);
    if (null != forwardedFor) {
      String originalIp = forwardedFor.split(",")[0];
      MDC.put(Constants.MDC_KEY_IP, originalIp);
    }
    String referrer = httpServletRequest.getHeader(HttpHeaders.REFERER);
    if (null != referrer) {
      MDC.put(Constants.MDC_KEY_REFERRER, referrer);
    }

    logger.info("resource: " + resource);

    try {
      chain.doFilter(request, response);

      final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
      MDC.put(Constants.MDC_KEY_STATUS, "" + httpServletResponse.getStatus());

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Got exception while processing the request", e);
      throw e;
    } finally {
      if (customLoggingAppender != null) {
        customLoggingAppender.finalWrite();
      }
    }
  }

  @Override
  public void destroy() {}
}
