package com.rs;

import org.springframework.core.env.Environment;

public class RSProfile {

  public static final String TEST = "test";
  public static final String STAGING = "staging";
  public static final String PREPROD = "preprod";
  public static final String PROD = "prod";

  public static final String UNITTEST = "unittest";
  public static final String LOCAL = "local";
  public static final String LOCALTEST = "localtest"; // profile for the test system, started local

  public static boolean isDevelopmentEnvironment(final Environment env) {
    return env.acceptsProfiles("default", LOCAL);
  }

  public static boolean isRemoteEnvironment(final Environment env) {
    return env.acceptsProfiles(TEST, STAGING, PREPROD, PROD);
  }
}
