package com.rs.model;

import java.time.LocalDate;

public class Term {

  private LocalDate startDate;

  private LocalDate endDate;

  public Term(final LocalDate startDate, final LocalDate endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public Term(){}

  public LocalDate getStartDate() {
    return startDate;
  }

  public Term setStartDate(final LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public Term setEndDate(final LocalDate endDate) {
    this.endDate = endDate;
    return this;
  }

  @Override
  public String toString() {
    return startDate + ", " + endDate;
  }

  public boolean contains(final LocalDate date) {
    return !date.isBefore(getStartDate()) && !date.isAfter(getEndDate());
  }

  @Override
  public boolean equals(final Object obj) {
    return ((Term) obj).getStartDate().isEqual(this.getStartDate()) && ((Term) obj).getEndDate().isEqual(this.getEndDate());
  }
}
