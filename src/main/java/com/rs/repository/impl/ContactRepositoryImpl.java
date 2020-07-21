package com.rs.repository.impl;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQuery;
import com.rs.model.Asset;
import com.rs.model.Contact;
import com.rs.model.QContact;
import com.rs.model.QReservationContact;
import com.rs.repository.custom.ContactCustomRepository;

public class ContactRepositoryImpl implements ContactCustomRepository {

  private static final QContact CONTACT = QContact.contact;
  private static final QReservationContact RESERVATION_CONTACT = QReservationContact.reservationContact;

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Contact> getContactsOfAReservation(final String reservationId) {
    final JPAQuery<Asset> query = new JPAQuery<>(entityManager);
    query.from(CONTACT)
      .from(RESERVATION_CONTACT)
      .where(CONTACT.id.eq(RESERVATION_CONTACT.contactId))
      .where(RESERVATION_CONTACT.reservationId.eq(reservationId));
    return query.select(CONTACT).fetch();
  }

  @Override
  public Optional<Contact> findByExternalId(final String externalId) {
    return Optional.ofNullable(new JPAQuery<>(entityManager)
      .from(CONTACT)
      .where(CONTACT.externalId.eq(externalId)).select(CONTACT).fetchFirst());
  }
}
