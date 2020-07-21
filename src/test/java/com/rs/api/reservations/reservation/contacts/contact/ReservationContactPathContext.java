package com.rs.api.reservations.reservation.contacts.contact;

import com.rs.api.BasePathContext;
import com.rs.api.reservations.reservation.ReservationPathContext;

import static com.rs.api.ContextConstants.VAR_EXTERNAL_ID;
import static com.rs.api.ContextConstants.VAR_RESERVATION_ID;

public class ReservationContactPathContext extends BasePathContext<ReservationContactPathContext> {

  public ReservationContactPathContext setReservationId(final String reservationId) {
    super.addParam(VAR_RESERVATION_ID, reservationId);
    return this;
  }

  public ReservationContactPathContext setExternalId(final String externalContactId) {
    super.addParam(VAR_EXTERNAL_ID, externalContactId);
    return this;
  }

  public String getReservationId() {
    return getParam(VAR_RESERVATION_ID);
  }

  public String getExternalId() {
    return getParam(VAR_EXTERNAL_ID);
  }

  public static ReservationContactPathContext of(final ReservationPathContext reservationPathContext, final String externalContactId) {
    return new ReservationContactPathContext()
      .setApp(reservationPathContext.getApp())
      .setExternalId(externalContactId);
  }
}
