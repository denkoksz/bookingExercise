package com.rs.api.reservations.reservation.contacts.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import static com.rs.api.ResponseConstants.CREATED;
import static com.rs.api.ResponseConstants.ID;
import static com.rs.api.ResponseConstants.META;
import static com.rs.api.ResponseConstants.MODIFIED;
import static com.rs.api.ResponseConstants.NAME;

@JsonPropertyOrder({META, ID, CREATED, MODIFIED, NAME})
public class ReservationContactResponse {

  @JsonProperty(META)
  private final ReservationContactMetaResponse meta;

  @JsonProperty(ID)
  private final String id;

  @JsonProperty(CREATED)
  private final String created;

  @JsonProperty(MODIFIED)
  private final String modified;

  @JsonProperty(NAME)
  private final String name;

  private ReservationContactResponse(final ReservationContactMetaResponse meta,
                                     final String id,
                                     final String created,
                                     final String modified,
                                     final String name) {
    this.meta = meta;
    this.id = id;
    this.created = created;
    this.modified = modified;
    this.name = name;
  }

  public ReservationContactMetaResponse getMeta() {
    return meta;
  }

  public String getId() {
    return id;
  }

  public String getCreated() {
    return created;
  }

  public String getModified() {
    return modified;
  }

  public String getName() {
    return name;
  }

  public static ReservationContactResponse of(final ReservationContactMetaResponse meta,
                                              final String id,
                                              final String created,
                                              final String modified,
                                              final String name) {
    return new ReservationContactResponse(meta, id, created, modified, name);
  }
}
