package com.rs.api.reservations;

import com.fasterxml.jackson.annotation.JsonProperty;

import static com.rs.api.ResponseConstants.TITLE;

public class AddReservationRequest {

  @JsonProperty(TITLE)
  private String title;

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }
}

