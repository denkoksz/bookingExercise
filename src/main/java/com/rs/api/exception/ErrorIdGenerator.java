package com.rs.api.exception;

import java.time.format.DateTimeFormatter;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
public class ErrorIdGenerator {

  private static final DateTimeFormatter MESSAGE_ID_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

  private final Environment env;

  public ErrorIdGenerator(final Environment env) {
    this.env = env;
  }

  public String getId() {
    return now().format(MESSAGE_ID_FORMATTER) + getCodeSuffix();
  }

  private String getCodeSuffix() {
    return env.getActiveProfiles().length > 0 ? env.getActiveProfiles()[0].toUpperCase() : "";
  }
}
