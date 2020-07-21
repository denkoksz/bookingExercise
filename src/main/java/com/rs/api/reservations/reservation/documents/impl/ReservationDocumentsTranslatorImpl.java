package com.rs.api.reservations.reservation.documents.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.api.MetaResponse;
import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.api.reservations.reservation.documents.ReservationDocumentResponse;
import com.rs.api.reservations.reservation.documents.ReservationDocumentsResponse;
import com.rs.api.reservations.reservation.documents.ReservationDocumentsTranslator;
import com.rs.api.reservations.reservation.documents.document.ReservationDocumentPathContext;
import com.rs.api.reservations.reservation.documents.document.ReservationDocumentTranslator;
import com.rs.model.ReservationDocument;
import com.rs.uris.Uris.PS;

import static com.rs.api.LinkBuilder.buildLink;
import static com.rs.api.ResponseConstants.SELF;

@Service
public class ReservationDocumentsTranslatorImpl implements ReservationDocumentsTranslator {

  @Autowired
  private ReservationDocumentTranslator reservationDocumentTranslator;

  @Override
  public ReservationDocumentsResponse translate(final ReservationPathContext reservationPathContext,
                                                final List<ReservationDocument> reservationDocuments) {
    return ReservationDocumentsResponse.of(meta(reservationPathContext),
      reservationAssetResponses(reservationPathContext, reservationDocuments));
  }

  private MetaResponse meta(final ReservationPathContext reservationPathContext) {
    return MetaResponse.empty()
      .addLink(SELF, buildLink(PS.V1.RESERVATIONS.RESERVATION.DOCUMENTS.URI, reservationPathContext));
  }

  private List<ReservationDocumentResponse> reservationAssetResponses(final ReservationPathContext reservationPathContext,
                                                                      final List<ReservationDocument> reservationDocuments) {
    return reservationDocuments.stream()
      .map(reservationDocument -> reservationDocumentTranslator.translate(
        ReservationDocumentPathContext.of(reservationPathContext, reservationDocument.getExternalId()), reservationDocument))
      .collect(Collectors.toList());
  }
}