package com.rs;

import ch.qos.logback.classic.spi.ILoggingEvent;
import net.logstash.logback.composite.CompositeJsonFormatter;
import net.logstash.logback.encoder.LogstashEncoder;

public class RSLogstashEncoder extends LogstashEncoder {

  @Override
  protected CompositeJsonFormatter<ILoggingEvent> createFormatter() {
    return new RSLogstashFormatter(this);
  }
}
