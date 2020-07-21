package com.rs.api.reservations.reservation.contacts;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rs.api.MetaResponse;
import com.rs.api.reservations.reservation.contacts.contact.ReservationContactResponse;

import static com.rs.api.ResponseConstants.ITEMS;
import static com.rs.api.ResponseConstants.META;
import static com.rs.api.ResponseConstants.TOTAL;

@JsonPropertyOrder({META, ITEMS, TOTAL})
public class ReservationContactsResponse {

  @JsonProperty(META)
  private final MetaResponse meta;

  @JsonProperty(ITEMS)
  private final java.util.List<ReservationContactResponse> items;

  @JsonProperty(TOTAL)
  private final Integer total;

  private ReservationContactsResponse(final MetaResponse meta,
                                      final List<ReservationContactResponse> items,
                                      final int total) {
    this.meta = meta;
    this.items = items;
    this.total = total;
  }

  public static ReservationContactsResponse of(final MetaResponse meta,
                                               final List<ReservationContactResponse> items,
                                               final int total) {
    return new ReservationContactsResponse(meta, items, total);
  }

  public MetaResponse getMeta() {
    return meta;
  }

  public java.util.List<ReservationContactResponse> getItems() {
    return items;
  }

  public Integer getTotal() {
    return total;
  }
}
