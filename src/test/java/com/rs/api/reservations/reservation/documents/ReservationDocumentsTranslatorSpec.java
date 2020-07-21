package com.rs.api.reservations.reservation.documents;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.AbstractSpec;
import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.model.Reservation;
import com.rs.model.ReservationDocument;
import com.rs.repository.service.ReservationDocumentRepositoryService;
import com.rs.service.ReservationDocumentService;
import com.rs.service.ReservationService;

import static com.rs.api.ResponseConstants.SELF;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ReservationDocumentsTranslatorSpec extends AbstractSpec {

  @Autowired
  private ReservationDocumentsTranslator reservationDocumentsTranslator;

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ReservationDocumentRepositoryService reservationDocumentRepositoryService;

  @Autowired
  private ReservationDocumentService reservationDocumentService;

  @Test
  public void meta() {
    final ReservationDocumentsResponse translation = reservationDocumentsTranslator.translate(getParams(), Collections.emptyList());

    assertThat(translation.getMeta().getLink(SELF), is("/api/v1/app/reservations/reservationId/documents"));
  }

  @Test
  public void emptyItems() {
    final ReservationDocumentsResponse translation = reservationDocumentsTranslator.translate(getParams(), Collections.emptyList());

    assertThat(translation.getItems().size(), is(0));
    assertThat(translation.getTotal(), is(0));
  }

  @Test
  public void withItems() {
    final Reservation reservation = reservationService.create("first");
    final ReservationDocument reservationDocument1 = reservationDocumentRepositoryService.create("reservationDocument1");
    reservationDocumentService.addReservationDocument(reservation.getId(), reservationDocument1.getExternalId());
    final ReservationDocument reservationDocument2 = reservationDocumentRepositoryService.create("reservationDocument2");
    reservationDocumentService.addReservationDocument(reservation.getId(), reservationDocument2.getExternalId());
    final List<ReservationDocument> reservationDocuments = reservationDocumentService.getReservationDocuments(reservation.getId());

    final ReservationDocumentsResponse translation = reservationDocumentsTranslator.translate(getParams(), reservationDocuments);

    assertThat(translation.getItems().size(), is(2));
    assertThat(translation.getTotal(), is(2));
    assertThat(translation.getItems().get(0).getExternalId(), is(reservationDocument1.getExternalId()));
    assertThat(translation.getItems().get(1).getExternalId(), is(reservationDocument2.getExternalId()));
  }

  private ReservationPathContext getParams() {
    return new ReservationPathContext()
      .setApp("app")
      .setReservationId("reservationId");
  }
}