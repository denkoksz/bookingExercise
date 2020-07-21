package com.rs.api.reservations.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rs.api.MetaResponse;

import static com.rs.api.ResponseConstants.ID;
import static com.rs.api.ResponseConstants.META;
import static com.rs.api.ResponseConstants.STATUS;
import static com.rs.api.ResponseConstants.TITLE;

@JsonPropertyOrder({META, ID, STATUS})
public class ReservationResponse {

  @JsonProperty(META)
  private final MetaResponse meta;

  @JsonProperty(ID)
  private final String id;

  @JsonProperty(STATUS)
  private final String status;

  @JsonProperty(TITLE)
  private final String title;

  private ReservationResponse(final MetaResponse meta, final String id, final String status, final String title) {
    this.meta = meta;
    this.id = id;
    this.status = status;
    this.title = title;
  }

  public MetaResponse getMeta() {
    return meta;
  }

  public String getId() {
    return id;
  }

  public static ReservationResponse of(final MetaResponse meta, final String id, final String status, final String title) {
    return new ReservationResponse(meta, id, status, title);
  }
}
