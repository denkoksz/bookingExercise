package com.rs.api.reservations.reservation.assets;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rs.api.exception.ServiceException;
import com.rs.model.Term;

import static com.rs.api.ResponseConstants.END_DATE;
import static com.rs.api.ResponseConstants.ID;
import static com.rs.api.ResponseConstants.QUANTITY;
import static com.rs.api.ResponseConstants.START_DATE;
import static com.rs.message.ErrorMessages.DATE_NULL;

public class AddAssetRequest {

  @JsonProperty(ID)
  private String id;

  @JsonProperty(QUANTITY)
  private Integer quantity;

  @JsonProperty(START_DATE)
  private String startDate;

  @JsonProperty(END_DATE)
  private String endDate;

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(final Integer quantity) {
    this.quantity = quantity;
  }

  public void setStartDate(final String startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(final String endDate) {
    this.endDate = endDate;
  }

  public Term getTerm() {
    checkDateNull(startDate, endDate);
    LocalDate startDate = LocalDate.parse(this.startDate);
    LocalDate endDate = LocalDate.parse(this.endDate);
    return new Term(startDate, endDate);
  }

  private void checkDateNull(String... dates){
    Arrays.stream(dates)
      .forEach((date) -> Optional.ofNullable(date)
        .or(() -> {
          throw new ServiceException(DATE_NULL, Optional.empty());
        }));
  }
}
