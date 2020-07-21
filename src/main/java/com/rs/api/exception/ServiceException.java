package com.rs.api.exception;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import com.rs.message.ServiceMessage;

public class ServiceException extends RuntimeException {

  private final ServiceMessage serviceMessage;
  private final Optional<Map<String, String>> params;

  public ServiceException(final ServiceMessage message, final Optional<Map<String, String>> params) {
    this.serviceMessage = message;
    this.params = params;
  }

  public ServiceMessage getServiceMessage() {
    return serviceMessage;
  }

  public HttpStatus getHttpStatus() {
    return serviceMessage.getStatus();
  }

  @Override
  public String getMessage() {
    return serviceMessage.getMessage(params);
  }
}
