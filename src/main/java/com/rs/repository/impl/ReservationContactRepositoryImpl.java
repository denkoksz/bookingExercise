package com.rs.repository.impl;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQuery;
import com.rs.model.QReservationContact;
import com.rs.model.ReservationContact;
import com.rs.repository.custom.ReservationContactCustomRepository;

public class ReservationContactRepositoryImpl implements ReservationContactCustomRepository {

  private static final QReservationContact RESERVATION_CONTACT = QReservationContact.reservationContact;

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<ReservationContact> findByReservationIdAndContactId(final String reservationId, final String contactId) {
    return Optional.ofNullable(new JPAQuery<>(entityManager)
      .from(RESERVATION_CONTACT)
      .where(RESERVATION_CONTACT.reservationId.eq(reservationId))
      .where(RESERVATION_CONTACT.contactId.eq(contactId))
      .select(RESERVATION_CONTACT)
      .fetchFirst());
  }
}
