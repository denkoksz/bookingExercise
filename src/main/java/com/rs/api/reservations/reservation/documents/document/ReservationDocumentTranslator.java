package com.rs.api.reservations.reservation.documents.document;

import com.rs.api.reservations.reservation.documents.ReservationDocumentResponse;
import com.rs.model.ReservationDocument;

public interface ReservationDocumentTranslator {

  ReservationDocumentResponse translate(ReservationDocumentPathContext reservationDocumentPathContext,
                                        ReservationDocument reservationDocument);
}
