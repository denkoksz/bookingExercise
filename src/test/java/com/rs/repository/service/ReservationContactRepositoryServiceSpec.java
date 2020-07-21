package com.rs.repository.service;

import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.api.exception.ServiceException;
import com.rs.model.Contact;
import com.rs.model.Reservation;
import com.rs.model.ReservationContact;
import com.rs.service.AbstractServiceSpec;

import static com.rs.message.ErrorMessages.RESERVATION_CONTACT_NOT_FOUND;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReservationContactRepositoryServiceSpec extends AbstractServiceSpec {

  @Autowired
  private ReservationRepositoryService reservationRepositoryService;

  @Autowired
  private ContactRepositoryService contactRepositoryService;

  @Autowired
  private ReservationContactRepositoryService reservationContactRepositoryService;

  @Test
  public void create() {
    final Contact contact = contactRepositoryService.create("externalId");
    final Reservation reservation = reservationRepositoryService.create("first");

    final ReservationContact reservationContact = reservationContactRepositoryService.create(reservation, contact);

    assertThat(reservationContact.getContactId(), is(contact.getId()));
    assertThat(reservationContact.getReservationId(), is(reservation.getId()));
  }

  @Test
  public void getByReservationAssetWithAssignment() {
    final Contact contact = contactRepositoryService.create("getByReservationContactWithAssignment");
    final Reservation reservation = reservationRepositoryService.create("first");
    final ReservationContact createdReservationContact = reservationContactRepositoryService.create(reservation, contact);

    final ReservationContact reservationContact = reservationContactRepositoryService.getByReservationAndContact(reservation, contact);

    assertThat(reservationContact, is(createdReservationContact));
    assertThat(reservationContact.getContactId(), is(contact.getId()));
    assertThat(reservationContact.getReservationId(), is(reservation.getId()));
  }

  @Test
  public void getByReservationAssetWithoutAssignment() {
    final Contact contact = contactRepositoryService.create("getByReservationContactWithoutAssignment");
    final Reservation reservation = reservationRepositoryService.create("first");

    final ServiceException serviceException =
      assertServiceException(() -> reservationContactRepositoryService.getByReservationAndContact(reservation, contact));

    assertThat(serviceException.getServiceMessage(), is(RESERVATION_CONTACT_NOT_FOUND));
  }

  @Test
  public void findByReservationAssetWithAssignment() {
    final Contact contact = contactRepositoryService.create("getByReservationContactWithAssignment");
    final Reservation reservation = reservationRepositoryService.create("first");
    final ReservationContact createdReservationContact = reservationContactRepositoryService.create(reservation, contact);

    final Optional<ReservationContact> reservationContact = reservationContactRepositoryService.findByReservationAndContact(reservation,
      contact);

    assertThat(reservationContact.isPresent(), is(true));
    assertThat(reservationContact.get(), is(createdReservationContact));
    assertThat(reservationContact.get().getContactId(), is(contact.getId()));
    assertThat(reservationContact.get().getReservationId(), is(reservation.getId()));
  }

  @Test
  public void findByReservationAssetWithoutAssignment() {
    final Contact contact = contactRepositoryService.create("getByReservationContactWithoutAssignment");
    final Reservation reservation = reservationRepositoryService.create("first");

    final Optional<ReservationContact> reservationContact = reservationContactRepositoryService.findByReservationAndContact(reservation,
      contact);

    assertThat(reservationContact.isPresent(), is(false));
  }

  @Test
  public void delete() {
    final Contact contact = contactRepositoryService.create("delete");
    final Reservation reservation = reservationRepositoryService.create("first");
    final ReservationContact reservationContact = reservationContactRepositoryService.create(reservation, contact);

    reservationContactRepositoryService.delete(reservationContact);

    final Optional<ReservationContact> deletedContact = reservationContactRepositoryService.findByReservationAndContact
      (reservation, contact);
    assertThat(deletedContact.isPresent(), is(false));
  }
}