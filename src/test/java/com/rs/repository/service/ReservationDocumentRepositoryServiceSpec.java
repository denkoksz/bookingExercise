package com.rs.repository.service;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.model.Reservation;
import com.rs.model.ReservationDocument;
import com.rs.model.ReservationToDocument;
import com.rs.service.AbstractServiceSpec;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReservationDocumentRepositoryServiceSpec extends AbstractServiceSpec {

  @Autowired
  private ReservationRepositoryService reservationRepositoryService;

  @Autowired
  private ReservationDocumentRepositoryService reservationDocumentRepositoryService;

  @Autowired
  private ReservationToDocumentRepositoryService reservationToDocumentRepositoryService;

  @Test
  public void create() {
    final Reservation reservation = reservationRepositoryService.create("first");
    final ReservationDocument reservationDocument = reservationDocumentRepositoryService.create("ReservationDocumentRepositoryServiceSpec");

    final ReservationToDocument reservationToDocument = reservationToDocumentRepositoryService.create(reservation, reservationDocument);

    assertThat(reservationToDocument.getReservationId(), is(reservation.getId()));
    assertThat(reservationToDocument.getDocumentId(), is(reservationDocument.getId()));
  }

  @Test
  public void findByReservationAndDocumentWithAssignment() {
    final Reservation reservation = reservationRepositoryService.create("first");
    final ReservationDocument reservationDocument = reservationDocumentRepositoryService.create("ReservationDocumentRepositoryServiceSpec");
    final ReservationToDocument reservationToDocument = reservationToDocumentRepositoryService.create(reservation, reservationDocument);

    final Optional<ReservationToDocument> getReservationToDocument =
      reservationToDocumentRepositoryService.findByReservationAndDocument(reservation, reservationDocument);

    assertThat(reservationToDocument.getReservationId(), is(getReservationToDocument.get().getReservationId()));
    assertThat(reservationToDocument.getDocumentId(), is(getReservationToDocument.get().getDocumentId()));
  }

  @Test
  public void findByReservationAndDocumentWithoutAssignment() {
    final Reservation reservation = reservationRepositoryService.create("first");
    final ReservationDocument reservationDocument = reservationDocumentRepositoryService.create("ReservationDocumentRepositoryServiceSpec");

    final Optional<ReservationToDocument> reservationToDocument =
      reservationToDocumentRepositoryService.findByReservationAndDocument(reservation, reservationDocument);

    assertThat(reservationToDocument.isPresent(), is(false));
  }

  @Test
  public void findAllByReservationIdWithAssignments() {
    final Reservation reservation = reservationRepositoryService.create("first");
    final ReservationDocument reservationDocument1 = reservationDocumentRepositoryService.create(
      "ReservationDocumentRepositoryServiceSpec1");
    final ReservationDocument reservationDocument2 = reservationDocumentRepositoryService.create(
      "ReservationDocumentRepositoryServiceSpec2");
    final ReservationToDocument reservationToDocument1 = reservationToDocumentRepositoryService.create(reservation, reservationDocument1);
    final ReservationToDocument reservationToDocument2 = reservationToDocumentRepositoryService.create(reservation, reservationDocument2);

    final List<ReservationDocument> reservationToDocuments =
      reservationDocumentRepositoryService.findAllByReservationId(reservation.getId());

    assertThat(reservationToDocuments.size(), is(2));
    assertThat(reservationToDocuments.get(0).getId(), is(reservationDocument1.getId()));
    assertThat(reservationToDocuments.get(1).getId(), is(reservationDocument2.getId()));
    assertThat(reservationToDocument1.equals(reservationToDocument2), is(false));
  }

  @Test
  public void findAllByReservationIdWithoutAssignment() {
    final Reservation reservation = reservationRepositoryService.create("first");
    reservationDocumentRepositoryService.create("ReservationDocumentRepositoryServiceSpec");

    final List<ReservationDocument> reservationToDocuments =
      reservationDocumentRepositoryService.findAllByReservationId(reservation.getId());

    assertThat(reservationToDocuments.size(), is(0));
  }
}
