package com.rs.api.reservations.reservation.contacts.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rs.api.MetaResponse;

import static com.rs.api.ResponseConstants.LINKS;
import static com.rs.api.ResponseConstants.TYPE;

@JsonPropertyOrder({LINKS, TYPE})
public class ReservationContactMetaResponse extends MetaResponse {

  @JsonProperty(TYPE)
  private final String type;

  private ReservationContactMetaResponse(final String type) {
    super();
    this.type = type;
  }

  public static ReservationContactMetaResponse of(final String type) {
    return new ReservationContactMetaResponse(type);
  }

  public String getType() {
    return type;
  }
}
