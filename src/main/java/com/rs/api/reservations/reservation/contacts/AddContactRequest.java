package com.rs.api.reservations.reservation.contacts;

import com.fasterxml.jackson.annotation.JsonProperty;

import static com.rs.api.ResponseConstants.ID;

public class AddContactRequest {

  @JsonProperty(ID)
  private String id;

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }
}
