package com.rs.service;

import java.util.List;

import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.api.reservations.reservation.contacts.contact.service.dto.ContactDetailsDTO;

public interface ReservationContactService {

  List<ContactDetailsDTO> getContactsOfAReservation(final ReservationPathContext pathContext);

  ContactDetailsDTO addContactByExternalId(ReservationPathContext pathContext, String externalContactId);

  void removeContactByExternalId(ReservationPathContext pathContext, String contactId);
}
