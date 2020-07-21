package com.rs.service.impl;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.rs.model.Reservation;

public class NoSQLReservation {

  private String id;

  private ZonedDateTime lastModified;

  private ZonedDateTime created;

  public String getId() {
    return id;
  }

  private NoSQLReservation setId(final String id) {
    this.id = id;
    return this;
  }

  public ZonedDateTime getCreated() {
    return created;
  }

  private NoSQLReservation setCreated(final ZonedDateTime created) {
    this.created = created;
    return this;
  }

  public ZonedDateTime getLastModified() {
    return lastModified;
  }

  private NoSQLReservation setLastModified(final ZonedDateTime lastModified) {
    this.lastModified = lastModified;
    return this;
  }

  public static NoSQLReservation of(final Reservation reservation) {
    return new NoSQLReservation()
      .setId(reservation.getId())
      .setLastModified(ZonedDateTime.ofInstant(reservation.getUpdatedTime().toInstant(), ZoneId.systemDefault()))
      .setCreated(ZonedDateTime.ofInstant(reservation.getCreatedTime().toInstant(), ZoneId.systemDefault()));
  }
}
