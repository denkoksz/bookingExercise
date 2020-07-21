package com.rs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.rs.model.EntityConsts.TITLE;

@Entity
@Table(name = EntityConsts.RESERVATION_TABLE_NAME)
public class Reservation extends AbstractPersistable<Reservation> {

  @Column(name = TITLE)
  private String title;

  public String getTitle() {
    return title;
  }

  public Reservation setTitle(String title) {
    this.title = title;
    return this;
  }
}
