package com.rs.service;

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.api.exception.ServiceException;
import com.rs.message.ErrorMessages;
import com.rs.model.Reservation;
import com.rs.model.ReservationDocument;
import com.rs.repository.service.ReservationDocumentRepositoryService;
import com.rs.repository.service.ReservationRepositoryService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReservationDocumentServiceSpec extends AbstractServiceSpec {

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ReservationRepositoryService reservationRepositoryService;

  @Autowired
  private ReservationDocumentRepositoryService reservationDocumentRepository;

  @Autowired
  private ReservationDocumentService reservationDocumentService;

  @Test
  public void addReservationDocumentUnknownReservation() {
    final ReservationDocument reservationDocument = reservationDocumentRepository.create("addReservationDocumentUnknownReservation");
    final ServiceException serviceException =
      assertServiceException(() -> reservationDocumentService.addReservationDocument("UNKNOWN", reservationDocument.getExternalId()));

    MatcherAssert.assertThat(serviceException.getServiceMessage().getCode(), Matchers.is(ErrorMessages.RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void addReservationDocumentWithoutDocument() {
    final Reservation reservation = reservationService.create("first");
    final ReservationDocument reservationDocument =
      reservationDocumentService.addReservationDocument(reservation.getId(), "addReservationDocumentWithoutDocument");

    assertThat(reservationDocument.getExternalId(), is("addReservationDocumentWithoutDocument"));
  }

  public void addReservationDocumentWithDocument() {
    final ReservationDocument reservationDocument = reservationDocumentRepository.create("addReservationDocumentUnknownReservation");
    final Reservation reservation = reservationService.create("first");
    final ReservationDocument addedReservationDocument =
      reservationDocumentService.addReservationDocument(reservation.getId(), reservationDocument.getExternalId());

    assertThat(addedReservationDocument.getExternalId(), is(reservationDocument.getExternalId()));
  }

  @Test
  public void getReservationDocumentsUnknownReservation() {
    final ServiceException serviceException =
      assertServiceException(() -> reservationDocumentService.getReservationDocuments("UNKNOWN"));

    MatcherAssert.assertThat(serviceException.getServiceMessage().getCode(), Matchers.is(ErrorMessages.RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void getReservationDocumentsWithoutDocument() {
    final Reservation reservation = reservationService.create("first");

    final List<ReservationDocument> reservationDocuments = reservationDocumentService.getReservationDocuments(reservation.getId());

    assertThat(reservationDocuments.size(), is(0));
  }

  @Test
  public void getReservationDocumentsWithDocuments() {
    final ReservationDocument reservationDocument1 = reservationDocumentRepository.create("addReservationDocumentUnknownReservation1");
    final ReservationDocument reservationDocument2 = reservationDocumentRepository.create("addReservationDocumentUnknownReservation2");
    final Reservation reservation = reservationService.create("first");
    reservationDocumentService.addReservationDocument(reservation.getId(), reservationDocument1.getExternalId());
    reservationDocumentService.addReservationDocument(reservation.getId(), reservationDocument2.getExternalId());

    final List<ReservationDocument> reservationDocuments = reservationDocumentService.getReservationDocuments(reservation.getId());

    assertThat(reservationDocuments.size(), is(2));
    assertThat(reservationDocuments.get(0).getExternalId(), is(reservationDocument1.getExternalId()));
    assertThat(reservationDocuments.get(1).getExternalId(), is(reservationDocument2.getExternalId()));
  }

  @Test
  public void getReservationDocumentUnknownReservation() {
    final ReservationDocument reservationDocument = reservationDocumentRepository.create("getReservationDocumentUnknownReservation");

    final ServiceException serviceException =
      assertServiceException(() -> reservationDocumentService.getReservationDocument("UNKNOWN", reservationDocument.getExternalId()));

    MatcherAssert.assertThat(serviceException.getServiceMessage().getCode(), Matchers.is(ErrorMessages.RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void getReservationDocumentWithoutDocument() {
    final Reservation reservation = reservationService.create("first");

    final ServiceException serviceException =
      assertServiceException(() -> reservationDocumentService.getReservationDocument(reservation.getId(), "UNKNOWN"));

    MatcherAssert.assertThat(serviceException.getServiceMessage().getCode(), Matchers.is(ErrorMessages.DOCUMENT_NOT_FOUND.getCode()));
  }

  @Test
  public void getReservationDocumentWithoutAssignment() {
    final Reservation noSQLReservation = reservationService.create("first");
    final ReservationDocument reservationDocument = reservationDocumentRepository.create("getReservationDocumentWithoutAssignment");

    final ServiceException serviceException =
      assertServiceException(() ->
        reservationDocumentService.getReservationDocument(noSQLReservation.getId(), reservationDocument.getExternalId()));

    MatcherAssert.assertThat(serviceException.getServiceMessage().getCode(), Matchers.is(ErrorMessages.RESERVATION_DOCUMENT_NOT_ASSIGNED
      .getCode()));
  }

  @Test
  public void getReservationDocumentWithDocument() {
    final ReservationDocument reservationDocument = reservationDocumentRepository.create("getReservationDocumentWithDocument");
    final Reservation reservation = reservationService.create("first");
    reservationDocumentService.addReservationDocument(reservation.getId(), reservationDocument.getExternalId());

    final ReservationDocument getReservationDocument = reservationDocumentService.getReservationDocument(reservation.getId(),
      reservationDocument.getExternalId());

    assertThat(getReservationDocument.getId(), is(reservationDocument.getId()));
  }
}