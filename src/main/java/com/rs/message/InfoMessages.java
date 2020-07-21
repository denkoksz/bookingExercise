package com.rs.message;

public class InfoMessages extends ServiceMessages {

  public static final ServiceMessage PROFILE_EXECUTION_EXECUTE =
    new ServiceMessage("I0001", "Method execution: ${" + InfoMessageConstants.FUNCTION_NAME + "} Execution Time: ${" +
      InfoMessageConstants.EXECUTION_TIME + "} ms");
}
