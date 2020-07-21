package com.rs.api.reservations.reservation.contacts.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rs.api.MetaResponse;
import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.api.reservations.reservation.contacts.ReservationContactsResponse;
import com.rs.api.reservations.reservation.contacts.ReservationContactsTranslator;
import com.rs.api.reservations.reservation.contacts.contact.ReservationContactResponse;
import com.rs.api.reservations.reservation.contacts.contact.ReservationContactTranslator;
import com.rs.api.reservations.reservation.contacts.contact.service.dto.ContactDetailsDTO;
import com.rs.uris.Uris;

import static com.rs.api.LinkBuilder.buildLink;
import static com.rs.api.ResponseConstants.SELF;

@Service
public class ReservationContactsTranslatorImpl implements ReservationContactsTranslator {

  private final ReservationContactTranslator reservationContactTranslator;

  public ReservationContactsTranslatorImpl(final ReservationContactTranslator reservationContactTranslator) {
    this.reservationContactTranslator = reservationContactTranslator;
  }

  @Override
  public ReservationContactsResponse translate(final ReservationPathContext pathContext, final List<ContactDetailsDTO> contacts) {
    return ReservationContactsResponse.of(meta(pathContext), reservationContactResponses(pathContext, contacts), contacts.size());
  }

  private MetaResponse meta(final ReservationPathContext reservationPathContext) {
    return MetaResponse.empty()
      .addLink(SELF, buildLink(Uris.PS.V1.RESERVATIONS.RESERVATION.CONTACTS.URI, reservationPathContext));
  }

  private List<ReservationContactResponse> reservationContactResponses(final ReservationPathContext reservationPathContext,
                                                                       final List<ContactDetailsDTO> contacts) {
    return contacts.stream()
      .map(contact ->
        reservationContactTranslator.translate(reservationPathContext, contact))
      .collect(Collectors.toList());
  }
}


