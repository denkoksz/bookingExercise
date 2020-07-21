package com.rs.api.reservations.reservation.contacts.contact.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import static com.rs.api.ResponseConstants.META;

public class ContactDetailsDTO {

  @JsonProperty(META)
  private ContactDetailsMetaDTO meta;
  private String id;
  private String created;
  private String modified;
  private String name;

  public ContactDetailsDTO setMeta(final ContactDetailsMetaDTO meta) {
    this.meta = meta;
    return this;
  }

  public ContactDetailsDTO setId(final String id) {
    this.id = id;
    return this;
  }

  public ContactDetailsDTO setCreated(final String created) {
    this.created = created;
    return this;
  }

  public ContactDetailsDTO setModified(final String modified) {
    this.modified = modified;
    return this;
  }

  public ContactDetailsDTO setName(final String name) {
    this.name = name;
    return this;
  }

  public ContactDetailsMetaDTO getMeta() {
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
}
