package com.rs.repository.service.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rs.api.exception.ServiceException;
import com.rs.model.Reservation;
import com.rs.model.ReservationDocument;
import com.rs.model.ReservationToDocument;
import com.rs.repository.ReservationToDocumentRepository;
import com.rs.repository.service.ReservationToDocumentRepositoryService;

import static com.rs.api.ContextConstants.VAR_DOCUMENT_ID;
import static com.rs.api.ContextConstants.VAR_RESERVATION_ID;
import static com.rs.message.ErrorMessages.RESERVATION_DOCUMENT_NOT_ASSIGNED;

@Service
public class ReservationToDocumentRepositoryServiceImpl implements ReservationToDocumentRepositoryService {

  private final ReservationToDocumentRepository reservationToDocumentRepository;

  public ReservationToDocumentRepositoryServiceImpl(ReservationToDocumentRepository reservationToDocumentRepository) {
    this.reservationToDocumentRepository = reservationToDocumentRepository;
  }

  @Override
  public Optional<ReservationToDocument> findByReservationAndDocument(final Reservation reservation,
                                                                      final ReservationDocument reservationDocument) {
    return reservationToDocumentRepository.findByReservationIdAndDocumentId(reservation.getId(), reservationDocument.getId());
  }

  @Override
  public ReservationToDocument getByReservationAndDocument(final Reservation reservation, final ReservationDocument reservationDocument) {
    return reservationToDocumentRepository.findByReservationIdAndDocumentId(reservation.getId(), reservationDocument.getId())
      .orElseThrow(() -> new ServiceException(RESERVATION_DOCUMENT_NOT_ASSIGNED,
        Optional.of(Map.of(VAR_RESERVATION_ID, reservation.getId(), VAR_DOCUMENT_ID, reservationDocument.getExternalId()))));
  }

  @Override
  public ReservationToDocument create(final Reservation reservation, final ReservationDocument reservationDocument) {
    return saveReservationToDocument(new ReservationToDocument()
      .setReservationId(reservation.getId())
      .setDocumentId(reservationDocument.getId()));
  }

  private ReservationToDocument saveReservationToDocument(final ReservationToDocument reservationToDocument) {
    return reservationToDocumentRepository.saveAndFlush(reservationToDocument);
  }
}
