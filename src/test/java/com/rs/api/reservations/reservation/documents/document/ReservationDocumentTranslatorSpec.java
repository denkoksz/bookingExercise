package com.rs.api.reservations.reservation.documents.document;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.AbstractSpec;
import com.rs.api.reservations.reservation.documents.ReservationDocumentResponse;
import com.rs.model.ReservationDocument;
import com.rs.repository.service.ReservationDocumentRepositoryService;

import static com.rs.api.ResponseConstants.SELF;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReservationDocumentTranslatorSpec extends AbstractSpec {

  @Autowired
  private ReservationDocumentTranslator reservationDocumentTranslator;

  @Autowired
  private ReservationDocumentRepositoryService reservationDocumentRepositoryService;

  @Test
  public void meta() {
    final ReservationDocument reservationDocument = reservationDocumentRepositoryService.create("ReservationDocumentTranslatorSpec");
    final ReservationDocumentResponse reservationDocumentResponse = reservationDocumentTranslator.translate(getParams(),
      reservationDocument);

    assertThat(reservationDocumentResponse.getMeta().getLink(SELF),
      is("/api/v1/app/reservations/reservationId/documents/ReservationDocumentTranslatorSpec"));
  }

  @Test
  public void translate() {
    final ReservationDocument reservationDocument = reservationDocumentRepositoryService.create("ReservationDocumentTranslatorSpec");
    final ReservationDocumentResponse reservationDocumentResponse = reservationDocumentTranslator.translate(getParams(),
      reservationDocument);

    assertThat(reservationDocumentResponse.getExternalId(), is(reservationDocument.getExternalId()));
  }

  private ReservationDocumentPathContext getParams() {
    return new ReservationDocumentPathContext()
      .setApp("app")
      .setReservationId("reservationId")
      .setDocumentId("ReservationDocumentTranslatorSpec");
  }
}
