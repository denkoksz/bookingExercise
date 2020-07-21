package com.rs.api.reservations;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rs.api.MetaResponse;
import com.rs.api.reservations.reservation.ReservationResponse;

import static com.rs.api.ResponseConstants.ITEMS;
import static com.rs.api.ResponseConstants.META;
import static com.rs.api.ResponseConstants.TOTAL;

@JsonPropertyOrder({META, ITEMS, TOTAL})
public class ReservationsResponse {

  @JsonProperty(META)
  private final MetaResponse meta;

  @JsonProperty(ITEMS)
  private final List<ReservationResponse> reservations;

  @JsonProperty(TOTAL)
  private final Integer total;

  public static ReservationsResponse of(final MetaResponse meta, final List<ReservationResponse> reservations, final Integer total) {
    return new ReservationsResponse(meta, reservations, total);
  }

  private ReservationsResponse(final MetaResponse meta, final List<ReservationResponse> reservations, final Integer total) {
    this.meta = meta;
    this.reservations = reservations;
    this.total = total;
  }

  public MetaResponse getMeta() {
    return meta;
  }

  @JsonIgnore
  public List<ReservationResponse> getReservations() {
    return reservations;
  }

  @JsonIgnore
  public Integer getTotal() {
    return total;
  }
}