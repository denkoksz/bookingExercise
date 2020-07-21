package com.rs;

import java.util.Map;
import java.util.Optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rs.message.InfoMessageConstants;

import static com.rs.message.InfoMessages.PROFILE_EXECUTION_EXECUTE;

@Aspect
public class ProfileExecutionAspect {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProfileExecutionAspect.class);

  @Around("@annotation(ProfileExecution) && execution(* *(..))")
  public Object profileExecuteMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
    final long startTime = System.currentTimeMillis();
    try {
      return joinPoint.proceed();
    } finally {
      final long executionTime = System.currentTimeMillis() - startTime;
      LOGGER.info(PROFILE_EXECUTION_EXECUTE.getLogMessage(
        Optional.of(Map.of(InfoMessageConstants.FUNCTION_NAME, joinPoint.getSignature().toString(), InfoMessageConstants
          .EXECUTION_TIME, Long.toString(executionTime)))));
    }
  }
}

