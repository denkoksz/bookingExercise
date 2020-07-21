package com.rs.api.reservations.reservation.contacts.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.service.ReservationContactService;
import com.rs.uris.Uris;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
public class ReservationContactController {

  @Autowired
  private ReservationContactService reservationContactService;

  @RequestMapping(path = Uris.PS.V1.RESERVATIONS.RESERVATION.CONTACTS.CONTACT.URI,
    method = DELETE,
    consumes = APPLICATION_JSON_VALUE,
    produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(OK)
  @Transactional
  public void removeContactFromReservation(final ReservationContactPathContext reservationContactPathContext) {
    reservationContactService
      .removeContactByExternalId(reservationPathContext(reservationContactPathContext), reservationContactPathContext.getExternalId());
  }

  private ReservationPathContext reservationPathContext(final ReservationContactPathContext reservationContactPathContext) {
    return new ReservationPathContext()
      .setApp(reservationContactPathContext.getApp())
      .setReservationId(reservationContactPathContext.getReservationId());
  }
}
