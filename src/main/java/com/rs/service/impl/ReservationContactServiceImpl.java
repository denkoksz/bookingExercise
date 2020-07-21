package com.rs.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.api.reservations.reservation.contacts.contact.ReservationExternalContactContext;
import com.rs.api.reservations.reservation.contacts.contact.service.ContactDetailsService;
import com.rs.api.reservations.reservation.contacts.contact.service.dto.ContactDetailsDTO;
import com.rs.model.Contact;
import com.rs.model.Reservation;
import com.rs.model.ReservationContact;
import com.rs.repository.service.ContactRepositoryService;
import com.rs.repository.service.ReservationContactRepositoryService;
import com.rs.service.ReservationContactService;
import com.rs.service.ReservationService;

@Service
public class ReservationContactServiceImpl implements ReservationContactService {

  private final ReservationService reservationService;

  private final ContactRepositoryService contactRepositoryService;

  private final ReservationContactRepositoryService reservationContactRepositoryService;

  private final ContactDetailsService contactDetailsService;

  public ReservationContactServiceImpl(final ReservationService reservationService,
                                       final ContactRepositoryService contactRepositoryService,
                                       final ReservationContactRepositoryService reservationContactRepositoryService,
                                       final ContactDetailsService contactDetailsService) {
    this.reservationService = reservationService;
    this.contactRepositoryService = contactRepositoryService;
    this.reservationContactRepositoryService = reservationContactRepositoryService;
    this.contactDetailsService = contactDetailsService;
  }

  @Override
  public List<ContactDetailsDTO> getContactsOfAReservation(final ReservationPathContext pathContext) {
    reservationService.getNoSQLReservationById(pathContext.getReservationId());
    return contactRepositoryService.getContactsOfAReservation(pathContext.getReservationId()).stream()
      .map(contact -> contactDetailsService.getContact(contactContext(pathContext, contact.getExternalId())))
      .collect(Collectors.toList());
  }

  @Override
  public ContactDetailsDTO addContactByExternalId(final ReservationPathContext pathContext, final String externalContactId) {
    final ContactDetailsDTO contactDetails = getContactDetails(pathContext, externalContactId);
    ensureReservationAndContactRelation(pathContext, externalContactId);
    return contactDetails;
  }

  @Override
  public void removeContactByExternalId(final ReservationPathContext pathContext, final String externalId) {
    final Reservation reservation = reservationService.getReservationById(pathContext.getReservationId());
    final Contact contact = contactRepositoryService.getByExternalId(externalId);
    final ReservationContact reservationContact = reservationContactRepositoryService.getByReservationAndContact(reservation, contact);
    reservationContactRepositoryService.delete(reservationContact);
  }

  private ReservationExternalContactContext contactContext(final ReservationPathContext pathContext, final String externalContactId) {
    return ReservationExternalContactContext.of(pathContext, externalContactId);
  }

  private ContactDetailsDTO getContactDetails(final ReservationPathContext pathContext, final String contactId) {
    return contactDetailsService.getContact(ReservationExternalContactContext.of(pathContext, contactId));
  }

  private ReservationContact ensureReservationAndContactRelation(final ReservationPathContext pathContext, final String externalContactId) {
    final Reservation reservation = reservationService.getReservationById(pathContext.getReservationId());
    final Contact contact = contactRepositoryService.findByExternalId(externalContactId)
      .orElseGet(() -> contactRepositoryService.create(externalContactId));
    return reservationContactRepositoryService.findByReservationAndContact(reservation, contact)
      .orElseGet(() -> reservationContactRepositoryService.create(reservation, contact));
  }
}
