package com.rs.repository.impl;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQuery;
import com.rs.model.QReservationToDocument;
import com.rs.model.ReservationToDocument;
import com.rs.repository.custom.ReservationToDocumentCustomRepository;

public class ReservationToDocumentRepositoryImpl implements ReservationToDocumentCustomRepository {

  private static final QReservationToDocument RESERVATION_TO_DOCUMENT = QReservationToDocument.reservationToDocument;

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<ReservationToDocument> findByReservationIdAndDocumentId(final String reservationId, final String documentId) {
    final JPAQuery<ReservationToDocument> query = new JPAQuery<>(entityManager);
    query.from(RESERVATION_TO_DOCUMENT)
      .where(RESERVATION_TO_DOCUMENT.reservationId.eq(reservationId))
      .where(RESERVATION_TO_DOCUMENT.documentId.eq(documentId));
    return Optional.ofNullable(query.select(RESERVATION_TO_DOCUMENT).fetchFirst());
  }
}
