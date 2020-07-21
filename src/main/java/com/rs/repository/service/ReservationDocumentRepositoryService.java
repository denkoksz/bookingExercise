package com.rs.repository.service;

import java.util.List;
import java.util.Optional;

import com.rs.model.ReservationDocument;

public interface ReservationDocumentRepositoryService {

  Optional<ReservationDocument> findByExternalId(String externalId);

  ReservationDocument getByExternalId(String externalId);

  ReservationDocument create(String externalId);

  List<ReservationDocument> findAllByReservationId(String reservationId);
}
