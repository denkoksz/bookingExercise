package com.rs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class ReservationServiceConfig {

  @Bean
  public ProfileExecutionAspect profileExecutionAspect() {
    return new ProfileExecutionAspect();
  }
}
