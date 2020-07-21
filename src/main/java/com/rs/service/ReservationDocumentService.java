package com.rs.service;

import java.util.List;

import com.rs.model.ReservationDocument;

public interface ReservationDocumentService {

  ReservationDocument addReservationDocument(String reservationId, String documentExternalId);

  ReservationDocument getReservationDocument(String reservationId, String documentExternalId);

  List<ReservationDocument> getReservationDocuments(String reservationId);
}
