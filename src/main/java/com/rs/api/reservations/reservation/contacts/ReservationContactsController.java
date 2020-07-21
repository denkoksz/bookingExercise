package com.rs.api.reservations.reservation.contacts;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.api.reservations.reservation.contacts.contact.ReservationContactResponse;
import com.rs.api.reservations.reservation.contacts.contact.ReservationContactTranslator;
import com.rs.api.reservations.reservation.contacts.contact.service.dto.ContactDetailsDTO;
import com.rs.service.ReservationContactService;
import com.rs.uris.Uris;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ReservationContactsController {

  private final ReservationContactService reservationContactService;

  private final ReservationContactsTranslator reservationContactsTranslator;

  private final ReservationContactTranslator reservationContactTranslator;

  public ReservationContactsController(final ReservationContactService reservationContactService,
                                       final ReservationContactsTranslator reservationContactsTranslator,
                                       final ReservationContactTranslator reservationContactTranslator) {
    this.reservationContactService = reservationContactService;
    this.reservationContactsTranslator = reservationContactsTranslator;
    this.reservationContactTranslator = reservationContactTranslator;
  }

  @RequestMapping(path = Uris.PS.V1.RESERVATIONS.RESERVATION.CONTACTS.URI,
    method = GET,
    produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(OK)
  @Transactional
  public ReservationContactsResponse getContacts(final ReservationPathContext reservationPathContext) {
    final List<ContactDetailsDTO> contacts = reservationContactService.getContactsOfAReservation(reservationPathContext);
    return reservationContactsTranslator.translate(reservationPathContext, contacts);
  }

  @RequestMapping(path = Uris.PS.V1.RESERVATIONS.RESERVATION.CONTACTS.URI,
    method = POST,
    consumes = APPLICATION_JSON_VALUE,
    produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(CREATED)
  @Transactional
  public ReservationContactResponse addContact(final ReservationPathContext reservationPathContext,
                                               @RequestBody final AddContactRequest addContactRequest) {
    final ContactDetailsDTO contact = reservationContactService.addContactByExternalId(reservationPathContext, addContactRequest.getId());
    return reservationContactTranslator.translate(reservationPathContext, contact);
  }
}
