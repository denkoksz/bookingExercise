package com.rs.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import static com.rs.api.ResponseConstants.STATUS;

@JsonPropertyOrder(STATUS)
public class RootResponse {

  @JsonProperty(STATUS)
  private final String status;

  private RootResponse(final String status) {
    this.status = status;
  }

  public static RootResponse of(final String status) {
    return new RootResponse(status);
  }
}
