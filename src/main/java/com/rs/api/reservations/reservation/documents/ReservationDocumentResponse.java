package com.rs.api.reservations.reservation.documents;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rs.api.MetaResponse;

import static com.rs.api.ResponseConstants.ID;
import static com.rs.api.ResponseConstants.META;
import static com.rs.api.ResponseConstants.TITLE;
import static com.rs.api.ResponseConstants.TYPE;

@JsonPropertyOrder({META, ID, TYPE, TITLE})
public class ReservationDocumentResponse {

  @JsonProperty(META)
  private final MetaResponse meta;

  @JsonProperty(ID)
  private final String externalId;

  @JsonProperty(TYPE)
  private final String type;

  @JsonProperty(TITLE)
  private final String title;

  private ReservationDocumentResponse(final MetaResponse meta,
                                      final String externalId,
                                      final String type,
                                      final String title) {
    this.meta = meta;
    this.externalId = externalId;
    this.type = type;
    this.title = title;
  }

  public static ReservationDocumentResponse of(final MetaResponse meta,
                                               final String externalId,
                                               final String type,
                                               final String title) {
    return new ReservationDocumentResponse(meta, externalId, type, title);
  }

  public MetaResponse getMeta() {
    return meta;
  }

  public String getExternalId() {
    return externalId;
  }
}

