package com.rs.api.reservations.reservation.documents.document.impl;

import org.springframework.stereotype.Service;

import com.rs.api.MetaResponse;
import com.rs.api.reservations.reservation.documents.ReservationDocumentResponse;
import com.rs.api.reservations.reservation.documents.document.ReservationDocumentPathContext;
import com.rs.api.reservations.reservation.documents.document.ReservationDocumentTranslator;
import com.rs.model.ReservationDocument;
import com.rs.uris.Uris;

import static com.rs.api.LinkBuilder.buildLink;
import static com.rs.api.ResponseConstants.SELF;

@Service
public class ReservationDocumentTranslatorImpl implements ReservationDocumentTranslator {

  @Override
  public ReservationDocumentResponse translate(final ReservationDocumentPathContext reservationDocumentPathContext,
                                               final ReservationDocument reservationDocument) {
    return ReservationDocumentResponse.of(meta(reservationDocumentPathContext),
      reservationDocument.getExternalId(),
      reservationDocument.getType(),
      reservationDocument.getTitle());
  }

  private MetaResponse meta(final ReservationDocumentPathContext reservationDocumentPathContext) {
    return MetaResponse.empty()
      .addLink(SELF, buildLink(Uris.PS.V1.RESERVATIONS.RESERVATION.DOCUMENTS.DOCUMENT.URI, reservationDocumentPathContext));
  }
}
