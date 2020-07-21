package com.rs.api.assets.asset.reservations;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rs.api.MetaResponse;
import com.rs.api.reservations.reservation.ReservationResponse;

import static com.rs.api.ResponseConstants.ITEMS;
import static com.rs.api.ResponseConstants.META;
import static com.rs.api.ResponseConstants.TOTAL;

@JsonPropertyOrder({META, ITEMS, TOTAL})
public class AssetReservationsResponse {

  @JsonProperty(META)
  private final MetaResponse meta;

  @JsonProperty(ITEMS)
  private final java.util.List<ReservationResponse> items;

  @JsonProperty(TOTAL)
  private final Integer total;

  private AssetReservationsResponse(final MetaResponse meta, final List<ReservationResponse> items) {
    this.meta = meta;
    this.items = items;
    this.total = items.size();
  }

  public static AssetReservationsResponse of(final MetaResponse meta, final List<ReservationResponse> items) {
    return new AssetReservationsResponse(meta, items);
  }

  public MetaResponse getMeta() {
    return meta;
  }

  public java.util.List<ReservationResponse> getItems() {
    return items;
  }

  public Integer getTotal() {
    return total;
  }
}
