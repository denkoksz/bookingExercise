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

import static com.rs.model.EntityConsts.CONTACT_ID;
import static com.rs.model.EntityConsts.CREATED_TIME;
import static com.rs.model.EntityConsts.RESERVATION_ID;

@Entity
@Table(name = EntityConsts.RESERVATION_CONTACT_TABLE_NAME)
@IdClass(ReservationContactId.class)
public class ReservationContact implements Serializable {

  @Id
  @Column(name = RESERVATION_ID)
  private String reservationId;

  @Id
  @Column(name = CONTACT_ID)
  private String contactId;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, name = CREATED_TIME)
  private Date created;

  public String getReservationId() {
    return reservationId;
  }

  public ReservationContact setReservationId(final String reservationId) {
    this.reservationId = reservationId;
    return this;
  }

  public String getContactId() {
    return contactId;
  }

  public ReservationContact setContactId(final String contactId) {
    this.contactId = contactId;
    return this;
  }

  @PrePersist
  protected void onCreate() {
    created = new Date();
  }
}


