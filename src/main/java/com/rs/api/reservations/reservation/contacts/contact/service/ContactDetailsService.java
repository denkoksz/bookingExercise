package com.rs.api.reservations.reservation.contacts.contact.service;

import com.rs.api.reservations.reservation.contacts.contact.ReservationExternalContactContext;
import com.rs.api.reservations.reservation.contacts.contact.service.dto.ContactDetailsDTO;

public interface ContactDetailsService {

  ContactDetailsDTO getContact(ReservationExternalContactContext context);
}
