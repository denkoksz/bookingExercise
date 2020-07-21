package com.rs.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rs.model.Reservation;
import com.rs.model.ReservationDocument;
import com.rs.repository.service.ReservationDocumentRepositoryService;
import com.rs.repository.service.ReservationToDocumentRepositoryService;
import com.rs.service.ReservationDocumentService;
import com.rs.service.ReservationService;

@Service
public class ReservationDocumentServiceImpl implements ReservationDocumentService {

  private final ReservationService reservationService;

  private final ReservationDocumentRepositoryService reservationDocumentRepositoryService;

  private final ReservationToDocumentRepositoryService reservationToDocumentRepositoryService;

  public ReservationDocumentServiceImpl(final ReservationService reservationService,
                                        final ReservationDocumentRepositoryService reservationDocumentRepositoryService,
                                        final ReservationToDocumentRepositoryService reservationToDocumentRepositoryService) {
    this.reservationService = reservationService;
    this.reservationDocumentRepositoryService = reservationDocumentRepositoryService;
    this.reservationToDocumentRepositoryService = reservationToDocumentRepositoryService;
  }

  @Override
  public ReservationDocument addReservationDocument(final String reservationId, final String documentExternalId) {
    final Reservation reservation = reservationService.getReservationById(reservationId);
    final ReservationDocument reservationDocument = reservationDocumentRepositoryService.findByExternalId(documentExternalId)
      .orElseGet(() -> reservationDocumentRepositoryService.create(documentExternalId));
    reservationToDocumentRepositoryService.findByReservationAndDocument(reservation, reservationDocument)
      .orElseGet(() -> reservationToDocumentRepositoryService.create(reservation, reservationDocument));
    return reservationDocument;
  }

  @Override
  public List<ReservationDocument> getReservationDocuments(final String reservationId) {
    final NoSQLReservation noSQLReservation = reservationService.getNoSQLReservationById(reservationId);
    return reservationDocumentRepositoryService.findAllByReservationId(noSQLReservation.getId());
  }

  @Override
  public ReservationDocument getReservationDocument(final String reservationId, final String documentExternalId) {
    final Reservation reservation = reservationService.getReservationById(reservationId);
    final ReservationDocument reservationDocument = reservationDocumentRepositoryService.getByExternalId(documentExternalId);
    reservationToDocumentRepositoryService.getByReservationAndDocument(reservation, reservationDocument);
    return reservationDocument;
  }
}
