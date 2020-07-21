package com.rs.api.reservations.reservation.impl;

import org.springframework.stereotype.Service;

import com.rs.api.MetaResponse;
import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.api.reservations.reservation.ReservationResponse;
import com.rs.api.reservations.reservation.ReservationTranslator;
import com.rs.model.Reservation;
import com.rs.uris.Uris;

import static com.rs.api.LinkBuilder.buildLink;
import static com.rs.api.ResponseConstants.ASSETS;
import static com.rs.api.ResponseConstants.CONTACTS;
import static com.rs.api.ResponseConstants.DOCUMENTS;
import static com.rs.api.ResponseConstants.RESERVATIONS;
import static com.rs.api.ResponseConstants.SELF;

@Service
public class ReservationTranslatorImpl implements ReservationTranslator {

  @Override
  public ReservationResponse translate(final ReservationPathContext pathContext,
                                       final Reservation reservation) {
    return ReservationResponse.of(meta(pathContext), reservation.getId(), "", reservation.getTitle());
  }

  private MetaResponse meta(final ReservationPathContext reservationPathContext) {
    return MetaResponse.empty()
      .addLink(ASSETS, buildLink(Uris.PS.V1.RESERVATIONS.RESERVATION.ASSETS.URI, reservationPathContext))
      .addLink(CONTACTS, buildLink(Uris.PS.V1.RESERVATIONS.RESERVATION.CONTACTS.URI, reservationPathContext))
      .addLink(DOCUMENTS, buildLink(Uris.PS.V1.RESERVATIONS.RESERVATION.DOCUMENTS.URI, reservationPathContext))
      .addLink(RESERVATIONS, buildLink(Uris.PS.V1.RESERVATIONS.URI, reservationPathContext))
      .addLink(SELF, buildLink(Uris.PS.V1.RESERVATIONS.RESERVATION.URI, reservationPathContext));
  }
}
