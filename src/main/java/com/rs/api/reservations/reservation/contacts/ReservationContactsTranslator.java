package com.rs.api.reservations.reservation.contacts;

import java.util.List;

import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.api.reservations.reservation.contacts.contact.service.dto.ContactDetailsDTO;

public interface ReservationContactsTranslator {

  ReservationContactsResponse translate(final ReservationPathContext reservationPathContext, final List<ContactDetailsDTO> contacts);
}
