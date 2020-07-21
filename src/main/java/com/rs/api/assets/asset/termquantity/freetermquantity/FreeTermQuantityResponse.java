package com.rs.api.assets.asset.termquantity.freetermquantity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import static com.rs.api.ResponseConstants.END_DATE;
import static com.rs.api.ResponseConstants.QUANTITY;
import static com.rs.api.ResponseConstants.START_DATE;

@JsonPropertyOrder({START_DATE, END_DATE, QUANTITY})
public class FreeTermQuantityResponse {

  @JsonProperty(START_DATE)
  private final String startDate;

  @JsonProperty(END_DATE)
  private final String endDate;

  @JsonProperty(QUANTITY)
  private final Long quantity;

  public FreeTermQuantityResponse(final String startDate, final String endDate, final Long quantity) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.quantity = quantity;
  }

  public String getStartDate() {
    return startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public Long getQuantity() {
    return quantity;
  }
}
