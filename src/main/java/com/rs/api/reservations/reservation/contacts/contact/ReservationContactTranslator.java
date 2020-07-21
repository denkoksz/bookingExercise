package com.rs.api.reservations.reservation.contacts.contact;

import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.api.reservations.reservation.contacts.contact.service.dto.ContactDetailsDTO;

public interface ReservationContactTranslator {

  ReservationContactResponse translate(final ReservationPathContext reservationPathContext, final ContactDetailsDTO contact);
}
