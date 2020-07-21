package com.rs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.rs.model.EntityConsts.EXTERNAL_ID;
import static com.rs.model.EntityConsts.TITLE;
import static com.rs.model.EntityConsts.TYPE;

@Entity
@Table(name = EntityConsts.RESERVATION_DOCUMENT_TABLE_NAME)
public class ReservationDocument extends AbstractPersistable<ReservationDocument> {

  @Column(name = EXTERNAL_ID)
  private String externalId;

  @Column(name = TYPE)
  private String type;

  @Column(name = TITLE)
  private String title;

  public String getExternalId() {
    return externalId;
  }

  public ReservationDocument setExternalId(final String externalId) {
    this.externalId = externalId;
    return this;
  }

  public String getType() {
    return type;
  }

  public String getTitle() {
    return title;
  }
}
