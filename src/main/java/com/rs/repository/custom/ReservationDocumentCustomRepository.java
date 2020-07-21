package com.rs.repository.custom;

import java.util.List;
import java.util.Optional;

import com.rs.model.ReservationDocument;

public interface ReservationDocumentCustomRepository {

  Optional<ReservationDocument> findByExternalId(String externalId);

  List<ReservationDocument> findAllByReservationId(String reservationId);
}
