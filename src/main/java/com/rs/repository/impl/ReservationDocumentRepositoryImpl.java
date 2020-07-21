package com.rs.repository.impl;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQuery;
import com.rs.model.QReservationDocument;
import com.rs.model.QReservationToDocument;
import com.rs.model.ReservationDocument;
import com.rs.repository.custom.ReservationDocumentCustomRepository;

public class ReservationDocumentRepositoryImpl implements ReservationDocumentCustomRepository {

  private static final QReservationDocument RESERVATION_DOCUMENT = QReservationDocument.reservationDocument;
  private static final QReservationToDocument RESERVATION_TO_DOCUMENT = QReservationToDocument.reservationToDocument;

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<ReservationDocument> findByExternalId(final String externalId) {
    final JPAQuery<ReservationDocument> query = new JPAQuery<>(entityManager);
    query.from(RESERVATION_DOCUMENT)
      .where(RESERVATION_DOCUMENT.externalId.eq(externalId));
    return Optional.ofNullable(query.select(RESERVATION_DOCUMENT).fetchFirst());
  }

  @Override
  public List<ReservationDocument> findAllByReservationId(final String reservationId) {
    final JPAQuery<ReservationDocument> query = new JPAQuery<>(entityManager);
    query.from(RESERVATION_DOCUMENT)
      .from(RESERVATION_TO_DOCUMENT)
      .where(RESERVATION_DOCUMENT.id.eq(RESERVATION_TO_DOCUMENT.documentId))
      .where(RESERVATION_TO_DOCUMENT.reservationId.eq(reservationId));
    return query.select(RESERVATION_DOCUMENT).fetch();
  }
}
