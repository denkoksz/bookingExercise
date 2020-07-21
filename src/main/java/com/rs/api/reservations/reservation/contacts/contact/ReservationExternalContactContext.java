package com.rs.api.reservations.reservation.contacts.contact;

import com.rs.api.BasePathContext;
import com.rs.api.reservations.reservation.ReservationPathContext;

import static com.rs.api.ContextConstants.VAR_CONTACT_ID;
import static com.rs.api.ContextConstants.VAR_RESERVATION_ID;

public class ReservationExternalContactContext extends BasePathContext<ReservationExternalContactContext> {

  public ReservationExternalContactContext setReservationId(final String reservationId) {
    super.addParam(VAR_RESERVATION_ID, reservationId);
    return this;
  }

  public ReservationExternalContactContext setExternalContactId(final String externalContactId) {
    super.addParam(VAR_CONTACT_ID, externalContactId);
    return this;
  }

  public String getExternalContactId() {
    return getParam(VAR_CONTACT_ID);
  }

  public static ReservationExternalContactContext of(final ReservationPathContext reservationPathContext, final String externalContactId) {
    return new ReservationExternalContactContext()
      .setApp(reservationPathContext.getApp())
      .setReservationId(reservationPathContext.getReservationId())
      .setExternalContactId(externalContactId);
  }
}
