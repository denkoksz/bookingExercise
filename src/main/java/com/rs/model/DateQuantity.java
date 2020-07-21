package com.rs.model;

import java.time.LocalDate;

public class DateQuantity {

  private LocalDate date;

  private Long quantity;

  public DateQuantity(final LocalDate date, final Long quantity) {
    this.date = date;
    this.quantity = quantity;
  }

  public LocalDate getDate() {
    return date;
  }

  public Long getQuantity() {
    return quantity;
  }
}
