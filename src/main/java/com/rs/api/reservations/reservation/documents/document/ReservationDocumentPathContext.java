package com.rs.api.reservations.reservation.documents.document;

import com.rs.api.BasePathContext;
import com.rs.api.reservations.reservation.ReservationPathContext;

import static com.rs.api.ContextConstants.VAR_DOCUMENT_ID;
import static com.rs.api.ContextConstants.VAR_RESERVATION_ID;

public class ReservationDocumentPathContext extends BasePathContext<ReservationDocumentPathContext> {

  public ReservationDocumentPathContext setReservationId(final String reservationId) {
    super.addParam(VAR_RESERVATION_ID, reservationId);
    return this;
  }

  public String getReservationId() {
    return getParam(VAR_RESERVATION_ID);
  }

  public ReservationDocumentPathContext setDocumentId(final String externalId) {
    super.addParam(VAR_DOCUMENT_ID, externalId);
    return this;
  }

  public String getDocumentId() {
    return getParam(VAR_DOCUMENT_ID);
  }

  public static ReservationDocumentPathContext of(final ReservationPathContext reservationPathContext, final String documentExternalId) {
    return new ReservationDocumentPathContext()
      .setApp(reservationPathContext.getApp())
      .setReservationId(reservationPathContext.getReservationId())
      .setDocumentId(documentExternalId);
  }
}
