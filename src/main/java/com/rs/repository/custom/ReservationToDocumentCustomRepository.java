package com.rs.repository.custom;

import java.util.Optional;

import com.rs.model.ReservationToDocument;

public interface ReservationToDocumentCustomRepository {

  Optional<ReservationToDocument> findByReservationIdAndDocumentId(String reservationId, String documentId);
}
