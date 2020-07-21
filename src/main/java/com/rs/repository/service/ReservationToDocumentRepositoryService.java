package com.rs.repository.service;

import java.util.Optional;

import com.rs.model.Reservation;
import com.rs.model.ReservationDocument;
import com.rs.model.ReservationToDocument;

public interface ReservationToDocumentRepositoryService {

  Optional<ReservationToDocument> findByReservationAndDocument(Reservation reservation, ReservationDocument reservationDocument);

  ReservationToDocument getByReservationAndDocument(Reservation reservation, ReservationDocument reservationDocument);

  ReservationToDocument create(Reservation reservation, ReservationDocument reservationDocument);
}
