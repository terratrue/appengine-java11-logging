package com.terratrue.logging;

class Constants {
  static final String MDC_KEY_HOST = "logging.googleapis.host";
  static final String MDC_KEY_HTTP_VERSION = "logging.googleapis.httpversion";
  static final String MDC_KEY_IP = "logging.googleapis.ip";
  static final String MDC_KEY_METHOD = "logging.googleapis.method";
  static final String MDC_KEY_REFERRER = "logging.googleapis.referrer";
  static final String MDC_KEY_RESOURCE = "logging.googleapis.resource";
  static final String MDC_KEY_STATUS = "logging.googleapis.status";
  static final String MDC_KEY_TRACE = "logging.googleapis.trace";
  static final String MDC_KEY_TRACE_ID = "logging.googleapis.traceid";
  static final String MDC_KEY_USER_AGENT = "logging.googleapis.useragent";

  static final String SYSTEM_ENV_NAME_GAE_INSTANCE = "GAE_INSTANCE";

  static final String APP_ENGINE_REQUEST_LOG_NAME = "appengine.googleapis.com%2Frequest_log";

  static final String APP_ENGINE_RESOURCE_NAME = "gae_app";

  static final String APP_ENGINE_HEADER_NAME_CLOUD_TRACE_CONTEXT = "X-Cloud-Trace-Context";
  static final String APP_ENGINE_HEADER_NAME_FORWARDED_FOR = "X-Forwarded-For";

  static final String APP_ENGINE_TRACE_FORMAT_STR = "projects/%s/traces/%s";
}
