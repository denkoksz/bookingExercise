package com.rs.repository.custom;

import java.util.List;
import java.util.Optional;

import com.rs.model.Contact;

public interface ContactCustomRepository {

  List<Contact> getContactsOfAReservation(String reservationId);

  Optional<Contact> findByExternalId(String externalId);
}
