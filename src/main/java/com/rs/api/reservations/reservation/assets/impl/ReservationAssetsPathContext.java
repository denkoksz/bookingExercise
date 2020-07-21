package com.rs.api.reservations.reservation.assets.impl;

import com.rs.api.BasePathContext;

import static com.rs.api.ContextConstants.DEFAULT_LIMIT;
import static com.rs.api.ContextConstants.DEFAULT_OFFSET;
import static com.rs.api.ContextConstants.LIMIT;
import static com.rs.api.ContextConstants.OFFSET;
import static com.rs.api.ContextConstants.VAR_RESERVATION_ID;

public class ReservationAssetsPathContext extends BasePathContext<ReservationAssetsPathContext> {

  public ReservationAssetsPathContext setReservationId(final String reservationId) {
    super.addParam(VAR_RESERVATION_ID, reservationId);
    return this;
  }

  public String getReservationId() {
    return getParam(VAR_RESERVATION_ID);
  }

  public ReservationAssetsPathContext setLimit(final String limit) {
    super.addParam(LIMIT, limit);
    return this;
  }

  public Integer getLimit() {
    return hasParam(LIMIT) ? Integer.parseInt(getParam(LIMIT)) : DEFAULT_LIMIT;
  }

  public ReservationAssetsPathContext setOffset(final String offset) {
    super.addParam(OFFSET, offset);
    return this;
  }

  public Integer getOffset() {
    return hasParam(OFFSET) ? Integer.parseInt(getParam(OFFSET)) : DEFAULT_OFFSET;
  }
}
