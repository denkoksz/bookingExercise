package com.rs;

import ch.qos.logback.core.spi.ContextAware;
import net.logstash.logback.LogstashFormatter;

public class RSLogstashFormatter extends LogstashFormatter {

  public RSLogstashFormatter(final ContextAware declaredOrigin) {
    super(declaredOrigin, false);
    getProviders().addMessage(new ServiceMessageJsonProvider());
  }
}
