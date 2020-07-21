package com.rs.api.exception;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.AbstractSpec;
import com.rs.RSProfile;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.assertThat;

public class ErrorIdGeneratorSpec extends AbstractSpec {

  @Autowired
  private ErrorIdGenerator errorIdGenerator;

  @Test
  public void errorCodeGeneration() {
    final String errorId = errorIdGenerator.getId();
    try {
      Thread.sleep(1);
    } catch (final InterruptedException e) {
      // do nothing
    }
    final String errorId2 = errorIdGenerator.getId();

    assertThat(errorId, endsWith(RSProfile.UNITTEST.toUpperCase()));
    assertThat(errorId, not(errorId2));
  }
}