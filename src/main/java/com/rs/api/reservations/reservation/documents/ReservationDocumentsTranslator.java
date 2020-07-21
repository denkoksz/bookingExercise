package com.rs.api.reservations.reservation.documents;

import java.util.List;

import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.model.ReservationDocument;

public interface ReservationDocumentsTranslator {

  ReservationDocumentsResponse translate(ReservationPathContext reservationPathContext, List<ReservationDocument> reservationDocuments);
}
