package com.rs.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import static com.rs.model.EntityConsts.ASSET_ID;
import static com.rs.model.EntityConsts.CREATED_TIME;
import static com.rs.model.EntityConsts.END_DATE;
import static com.rs.model.EntityConsts.QUANTITY;
import static com.rs.model.EntityConsts.RESERVATION_ID;
import static com.rs.model.EntityConsts.START_DATE;

@Entity
@Table(name = EntityConsts.RESERVATION_ASSET_TABLE_NAME)
@IdClass(ReservationAssetId.class)
public class ReservationAsset implements Serializable {

  @Id
  @Column(name = RESERVATION_ID)
  private String reservationId;

  @Id
  @Column(name = ASSET_ID)
  private String assetId;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, name = CREATED_TIME)
  private Date created;

  @Column(nullable = false, name = QUANTITY)
  private Integer quantity;

  @Column(nullable = false, name = START_DATE, columnDefinition = "varchar(20)")
  private LocalDate startDate;

  @Column(nullable = false, name = END_DATE, columnDefinition = "varchar(20)")
  private LocalDate endDate;

  public String getReservationId() {
    return reservationId;
  }

  public ReservationAsset setReservationId(final String reservationId) {
    this.reservationId = reservationId;
    return this;
  }

  public String getAssetId() {
    return assetId;
  }

  public ReservationAsset setAssetId(final String assetId) {
    this.assetId = assetId;
    return this;
  }

  @PrePersist
  protected void onCreate() {
    created = new Date();
  }

  public Integer getQuantity() {
    return quantity;
  }

  public ReservationAsset setQuantity(final Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public ReservationAsset setStartDate(final LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public ReservationAsset setEndDate(final LocalDate endDate) {
    this.endDate = endDate;
    return this;
  }

  public Term getTerm() {
    return new Term(startDate, endDate);
  }

  public ReservationAsset setTerm(final Term term) {
    this.startDate = term.getStartDate();
    this.endDate = term.getEndDate();
    return this;
  }
}
