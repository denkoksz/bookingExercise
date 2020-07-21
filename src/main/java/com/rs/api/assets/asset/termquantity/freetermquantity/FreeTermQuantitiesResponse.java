package com.rs.api.assets.asset.termquantity.freetermquantity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import static com.rs.api.ResponseConstants.ITEMS;
import static com.rs.api.ResponseConstants.TOTAL;

@JsonPropertyOrder({TOTAL, ITEMS})
public class FreeTermQuantitiesResponse {

  @JsonProperty(ITEMS)
  private final List<FreeTermQuantityResponse> freeTermQuantityResponses;

  @JsonProperty(TOTAL)
  private final Integer total;

  public FreeTermQuantitiesResponse(final List<FreeTermQuantityResponse> freeTermQuantityResponses, final Integer total) {
    this.freeTermQuantityResponses = freeTermQuantityResponses;
    this.total = total;
  }

  public List<FreeTermQuantityResponse> getFreeTermQuantityResponses() {
    return freeTermQuantityResponses;
  }

  public Integer getTotal() {
    return total;
  }
}
