package com.rs.model;

public class TermQuantity {

  private Term term;

  private Integer quantity;

  public TermQuantity(final Term term, final Integer quantity) {
    this.term = term;
    this.quantity = quantity;
  }

  public Term getTerm() {
    return term;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(final Integer quantity) {
    this.quantity = quantity;
  }
}
