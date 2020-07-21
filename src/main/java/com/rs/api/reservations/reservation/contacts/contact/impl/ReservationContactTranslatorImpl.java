package com.rs.api.reservations.reservation.contacts.contact.impl;

import org.springframework.stereotype.Service;

import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.api.reservations.reservation.contacts.contact.ReservationContactMetaResponse;
import com.rs.api.reservations.reservation.contacts.contact.ReservationContactResponse;
import com.rs.api.reservations.reservation.contacts.contact.ReservationContactTranslator;
import com.rs.api.reservations.reservation.contacts.contact.service.dto.ContactDetailsDTO;

@Service
public class ReservationContactTranslatorImpl implements ReservationContactTranslator {

  @Override
  public ReservationContactResponse translate(final ReservationPathContext reservationPathContext, final ContactDetailsDTO contact) {
    return ReservationContactResponse
      .of(meta(contact), contact.getId(), contact.getCreated(), contact.getModified(), contact.getName());
  }

  private ReservationContactMetaResponse meta(final ContactDetailsDTO contact) {
    return ReservationContactMetaResponse.of(contact.getMeta().getType());
  }
}
