package com.rs.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

public class IdGeneratorServiceSpec extends AbstractServiceSpec {

  @Autowired
  private IdGeneratorService idGeneratorService;

  @Test
  public void testGenerate() {
    final String firstId = idGeneratorService.getId("XX");
    final String secondId = idGeneratorService.getId("XX");
    assertThat(firstId, not(secondId));
  }
}
