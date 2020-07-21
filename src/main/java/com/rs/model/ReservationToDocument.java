package com.rs.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import static com.rs.model.EntityConsts.CREATED_TIME;
import static com.rs.model.EntityConsts.DOCUMENT_ID;
import static com.rs.model.EntityConsts.RESERVATION_ID;

@Entity
@Table(name = EntityConsts.RESERVATION_TO_DOCUMENT_TABLE_NAME)
@IdClass(ReservationToDocumentId.class)
public class ReservationToDocument implements Serializable {

  @Id
  @Column(name = RESERVATION_ID)
  private String reservationId;

  @Id
  @Column(name = DOCUMENT_ID)
  private String documentId;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, name = CREATED_TIME)
  private Date createdTime;

  public String getReservationId() {
    return reservationId;
  }

  public ReservationToDocument setReservationId(final String reservationId) {
    this.reservationId = reservationId;
    return this;
  }

  public String getDocumentId() {
    return documentId;
  }

  public ReservationToDocument setDocumentId(final String documentId) {
    this.documentId = documentId;
    return this;
  }

  @PrePersist
  protected void onCreateTime() {
    createdTime = new Date();
  }
}
