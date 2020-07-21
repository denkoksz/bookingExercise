package com.rs;

import java.io.IOException;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonGenerator;
import com.rs.message.ServiceMessage;
import com.rs.message.ServiceMessages;
import net.logstash.logback.composite.JsonWritingUtils;
import net.logstash.logback.composite.loggingevent.MessageJsonProvider;

import static ch.qos.logback.classic.Level.DEBUG;
import static ch.qos.logback.classic.Level.ERROR;
import static ch.qos.logback.classic.Level.INFO;
import static ch.qos.logback.classic.Level.TRACE;
import static ch.qos.logback.classic.Level.WARN;
import static com.rs.message.ServiceMessage.PREFIX;
import static com.rs.message.ServiceMessages.DEFAULT_DEBUG_CODE;
import static com.rs.message.ServiceMessages.DEFAULT_ERROR_CODE;
import static com.rs.message.ServiceMessages.DEFAULT_INFO_CODE;
import static com.rs.message.ServiceMessages.DEFAULT_LOG_CODE;
import static com.rs.message.ServiceMessages.DEFAULT_WARNING_CODE;
import static java.util.Optional.empty;

public class ServiceMessageJsonProvider extends MessageJsonProvider {

  @Override
  public void writeTo(final JsonGenerator generator, final ILoggingEvent event) throws IOException {
    JsonWritingUtils.writeStringField(generator, getFieldName(), getFormattedMessage(event));
  }

  private String getFormattedMessage(final ILoggingEvent event) {
    final String message = event.getFormattedMessage();
    return message.startsWith(PREFIX) ? message : new ServiceMessage(getCode(event), message).getLogMessage(empty());
  }

  private String getCode(final ILoggingEvent event) {
    if (event.getLevel() == DEBUG) {
      return DEFAULT_DEBUG_CODE;
    } else if (event.getLevel() == INFO) {
      return DEFAULT_INFO_CODE;
    } else if (event.getLevel() == WARN) {
      return DEFAULT_WARNING_CODE;
    } else if (event.getLevel() == ERROR) {
      return DEFAULT_ERROR_CODE;
    } else if (event.getLevel() == TRACE) {
      return ServiceMessages.DEFAULT_TRACE_CODE;
    } else {
      return DEFAULT_LOG_CODE;
    }
  }
}
