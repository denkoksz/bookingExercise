package com.rs.repository.service;

import java.util.List;
import java.util.Optional;

import com.rs.model.Contact;

public interface ContactRepositoryService {

  Contact create(String externalId);

  Optional<Contact> findByExternalId(final String externalContactId);

  Contact getById(String contactId);

  Contact getByExternalId(String externalId);

  List<Contact> getContactsOfAReservation(String reservationId);
}
