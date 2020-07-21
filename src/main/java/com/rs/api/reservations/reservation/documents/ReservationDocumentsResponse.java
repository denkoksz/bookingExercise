package com.rs.api.reservations.reservation.documents;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rs.api.MetaResponse;

import static com.rs.api.ResponseConstants.ITEMS;
import static com.rs.api.ResponseConstants.META;
import static com.rs.api.ResponseConstants.TOTAL;

@JsonPropertyOrder({META, ITEMS, TOTAL})
public class ReservationDocumentsResponse {

  @JsonProperty(META)
  private final MetaResponse meta;

  @JsonProperty(ITEMS)
  private final java.util.List<ReservationDocumentResponse> items;

  @JsonProperty(TOTAL)
  private final Integer total;

  private ReservationDocumentsResponse(final MetaResponse meta,
                                       final List<ReservationDocumentResponse> items) {
    this.meta = meta;
    this.items = items;
    this.total = items.size();
  }

  public static ReservationDocumentsResponse of(final MetaResponse meta,
                                                final List<ReservationDocumentResponse> items) {
    return new ReservationDocumentsResponse(meta, items);
  }

  public MetaResponse getMeta() {
    return meta;
  }

  public java.util.List<ReservationDocumentResponse> getItems() {
    return items;
  }

  public Integer getTotal() {
    return total;
  }
}
