package com.rs.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ErrorMessageResponse {

  private final String errorCode;
  private final String errorMessage;
  private final String errorId;

  public ErrorMessageResponse(final String errorCode, final String errorMessage, final String errorId) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.errorId = errorId;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public String getErrorId() {
    return errorId;
  }
}
