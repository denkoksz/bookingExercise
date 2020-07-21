package com.rs.api.reservations.reservation.assets.asset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rs.api.MetaResponse;

import static com.rs.api.ResponseConstants.ID;
import static com.rs.api.ResponseConstants.META;

@JsonPropertyOrder({META, ID})
public class ReservationAssetResponse {

  @JsonProperty(META)
  private final MetaResponse meta;

  @JsonProperty(ID)
  private final String id;

  private ReservationAssetResponse(final MetaResponse meta, final String id) {
    this.meta = meta;
    this.id = id;
  }

  public MetaResponse getMeta() {
    return meta;
  }

  public String getId() {
    return id;
  }

  public static ReservationAssetResponse of(final MetaResponse meta, final String id) {
    return new ReservationAssetResponse(meta, id);
  }
}

