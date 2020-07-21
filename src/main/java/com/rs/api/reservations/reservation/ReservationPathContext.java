package com.rs.api.reservations.reservation;

import com.rs.api.BasePathContext;
import com.rs.api.ContextConstants;

public class ReservationPathContext extends BasePathContext<ReservationPathContext> {

  public static ReservationPathContext of(final String app, final String reservationId) {
    return new ReservationPathContext()
      .setApp(app)
      .setReservationId(reservationId);
  }

  public ReservationPathContext setReservationId(final String reservationId) {
    super.addParam(ContextConstants.VAR_RESERVATION_ID, reservationId);
    return this;
  }

  public String getReservationId() {
    return getParam(ContextConstants.VAR_RESERVATION_ID);
  }
}

