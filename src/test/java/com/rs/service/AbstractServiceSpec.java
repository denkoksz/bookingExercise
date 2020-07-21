package com.rs.service;

import java.util.concurrent.Callable;

import org.springframework.transaction.annotation.Transactional;

import com.rs.AbstractSpec;
import com.rs.api.exception.ServiceException;
import junit.framework.AssertionFailedError;

@Transactional
public abstract class AbstractServiceSpec extends AbstractSpec {

  protected ServiceException assertServiceException(final Callable callable) {
    try {
      callable.call();
    } catch (final Throwable throwable) {
      if (throwable instanceof ServiceException) {
        return (ServiceException) throwable;
      }
      throw createAssertionFailedError(throwable);
    }
    throw createAssertionFailedError();
  }

  protected Exception assertException(final Callable callable) {
    try {
      callable.call();
    } catch (final Exception exception) {
      return exception;
    }
    throw createAssertionFailedError();
  }

  private AssertionFailedError createAssertionFailedError(final Throwable throwable) {
    final String message = "Expected ServiceException to be thrown, but " + throwable.getMessage() + " was thrown.";
    return new AssertionFailedError(message);
  }

  private AssertionFailedError createAssertionFailedError() {
    final String message = "Expected ServiceException to be thrown, but nothing was thrown.";
    return new AssertionFailedError(message);
  }
}
