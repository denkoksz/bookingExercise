package com.rs.api.exception;

import java.time.format.DateTimeParseException;

import org.hibernate.StaleStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rs.api.response.ErrorMessageResponse;
import com.rs.message.ServiceMessage;

import static com.rs.message.ErrorMessages.DATE_FORMAT_ERROR;
import static com.rs.message.ErrorMessages.STATE_CHANGED_REFRESH_REQUIRED;
import static com.rs.message.ErrorMessages.UNKNOWN;
import static java.util.Optional.empty;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private final Logger LOGGER = LoggerFactory.getLogger(getClass());

  private final ErrorIdGenerator errorIdGenerator;

  public GlobalExceptionHandler(ErrorIdGenerator errorIdGenerator) {
    this.errorIdGenerator = errorIdGenerator;
  }

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<ErrorMessageResponse> handleError(final ServiceException e) {
    return responseEntity(e.getServiceMessage(), e.getMessage(), e);
  }

  @ExceptionHandler(StaleStateException.class)
  public ResponseEntity<ErrorMessageResponse> handleError(final StaleStateException e) {
    return createResponse(STATE_CHANGED_REFRESH_REQUIRED, e);
  }

  @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
  public ResponseEntity<ErrorMessageResponse> handleError(final ObjectOptimisticLockingFailureException e) {
    return createResponse(STATE_CHANGED_REFRESH_REQUIRED, e);
  }

  @ExceptionHandler(DateTimeParseException.class)
  public ResponseEntity<ErrorMessageResponse> handleError(final DateTimeParseException e) {
    return createResponse(DATE_FORMAT_ERROR, e);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessageResponse> handleError(final Exception e) {
    return createResponse(UNKNOWN, e);
  }

  private ResponseEntity<ErrorMessageResponse> createResponse(final ServiceMessage serviceMessage, final Throwable exception) {
    return responseEntity(serviceMessage, serviceMessage.getMessage(empty()), exception);
  }

  private ResponseEntity<ErrorMessageResponse> responseEntity(final ServiceMessage serviceMessage,
                                                              final String errorMessage,
                                                              final Throwable exception) {
    return new ResponseEntity<>(errorResponse(serviceMessage.getCode(), errorMessage, exception), serviceMessage.getStatus());
  }

  private ErrorMessageResponse errorResponse(final String errorCode, final String errorMessage, final Throwable exception) {
    final String id = errorIdGenerator.getId();
    LOGGER.error("Exception was thrown: {}", id, exception);
    return new ErrorMessageResponse(errorCode, errorMessage, id);
  }
}

