package com.rs.service.impl;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.rs.service.IdGeneratorService;

import static java.time.Instant.now;

@Service
public class IdGeneratorServiceImpl implements IdGeneratorService {

  private long lastEpochSecond = 0;
  private int lastNano = 0;
  private final StringBuilder id = new StringBuilder(50);
  private int index = 0;

  @Override
  public synchronized String getId(final String prefix) {
    id.setLength(0);
    Instant now = now();
    long epochSecond = now.getEpochSecond();
    int nano = now.getNano();
    id.append(prefix).append(Long.toHexString(epochSecond)).append(Integer.toHexString(nano));
    if (lastEpochSecond == epochSecond && lastNano == nano) {
      id.append(index++);
    } else {
      index = 0;
    }
    lastEpochSecond = epochSecond;
    lastNano = nano;
    return id.toString();
  }
}