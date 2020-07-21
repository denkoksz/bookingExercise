package com.rs.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ServiceMessage {

  public final static String PREFIX = "PS";
  private final String code;
  private final HttpStatus status;
  private final String message;

  public ServiceMessage(final String code, final HttpStatus status, final String message) {
    this.code = code;
    this.status = status;
    this.message = message;
  }

  public ServiceMessage(final String code, final String message) {
    this.code = code;
    this.status = BAD_REQUEST;
    this.message = message;
  }

  public String getMessage(final Optional<Map<String, String>> params) {
    return StrSubstitutor.replace(message, params.orElseGet(HashMap::new));
  }

  public String getLogMessage(final Optional<Map<String, String>> params) {
    return PREFIX + "-" + code + " - " + getMessage(params);
  }

  public HttpStatus getStatus() {
    return status;
  }

  public String getCode() {
    return code;
  }
}
