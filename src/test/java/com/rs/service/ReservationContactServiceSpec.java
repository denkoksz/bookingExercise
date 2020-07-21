package com.rs.service;

import java.util.List;
import java.util.concurrent.Callable;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import com.rs.api.exception.ServiceException;
import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.api.reservations.reservation.contacts.contact.service.dto.ContactDetailsDTO;
import com.rs.api.reservations.reservation.contacts.contact.service.dto.ContactDetailsMetaDTO;
import com.rs.auth.Oauth2ClientConfig;
import com.rs.message.ErrorMessages;
import com.rs.model.Contact;
import com.rs.model.Reservation;
import com.rs.repository.service.ContactRepositoryService;
import com.rs.repository.service.ReservationContactRepositoryService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

public class ReservationContactServiceSpec extends AbstractServiceSpec {

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ReservationContactService reservationContactService;

  @Autowired
  private ContactRepositoryService contactRepositoryService;

  @Autowired
  private ReservationContactRepositoryService reservationContactRepositoryService;

  @MockBean
  @Qualifier(Oauth2ClientConfig.CONTACT_CLIENT_CREDENTIALS_QUALIFIER)
  private OAuth2RestTemplate restTemplate;

  @Test
  public void getUnknownReservationContacts() {
    final ServiceException serviceException =
      assertServiceException(() ->
        reservationContactService.getContactsOfAReservation(ReservationPathContext.of("APP", "UNKNOWN")));

    MatcherAssert.assertThat(serviceException.getServiceMessage().getCode(), Matchers.is(ErrorMessages.RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void getReservationWithoutContacts() {
    final Reservation reservation = reservationService.create("first");
    final List<ContactDetailsDTO> resp =
      reservationContactService.getContactsOfAReservation(ReservationPathContext.of("APP", reservation.getId()));
    assertThat(resp.size(), is(0));
  }

  @Test
  public void getReservationWithContacts() {
    when(restTemplate
      .getForEntity(eq("https://contact-test-uri.com/api/contacts/v1/app/contacts/contactId"),
        eq(ContactDetailsDTO.class)))
      .thenReturn(new ResponseEntity<>(response(), OK));
    final Reservation reservation = reservationService.create("first");
    reservationContactService.addContactByExternalId(ReservationPathContext.of("app", reservation.getId()), "contactId");

    final List<ContactDetailsDTO> resp =
      reservationContactService.getContactsOfAReservation(ReservationPathContext.of("app", reservation.getId()));
    final ContactDetailsDTO contact = resp.get(0);

    assertThat(resp.size(), is(1));
    assertThat(contact.getMeta().getType(), is("individual"));
    assertThat(contact.getId(), is("Id"));
    assertThat(contact.getName(), is("Name"));
    assertThat(contact.getModified(), is("Modified"));
    assertThat(contact.getCreated(), is("Created"));
  }

  @Test
  public void contactNotFoundOnDAMSide() {
    when(restTemplate
      .getForEntity(eq("https://contact-test-uri.com/api/contacts/v1/app/contacts/wrongId"),
        eq(ContactDetailsDTO.class)))
      .thenThrow(new HttpClientErrorException(NOT_FOUND));
    final Reservation reservation = reservationService.create("first");
    final Contact wrongContact = contactRepositoryService.create("wrongId");
    reservationContactRepositoryService.create(reservationService.getReservationById(reservation.getId()), wrongContact);

    final ServiceException ex = assertServiceException(() ->
      reservationContactService.getContactsOfAReservation(ReservationPathContext.of("app", reservation.getId())));

    Assert.assertThat(ex.getHttpStatus(), is(NOT_FOUND));
    Assert.assertThat(ex.getServiceMessage().getCode(), Is.is(ErrorMessages.CONTACT_NOT_FOUND.getCode()));
  }

  @Test
  public void deleteReservationContactUnknownReservation() {
    final Contact contact = contactRepositoryService.create("deleteReservationContactUnknownReservation");

    final ServiceException serviceException =
      assertServiceException((Callable<Void>) () -> {
        reservationContactService.removeContactByExternalId(unknownReservationPathContext(), contact.getId());
        return null;
      });

    MatcherAssert.assertThat(serviceException.getServiceMessage().getCode(), Matchers.is(ErrorMessages.RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void deleteReservationContactUnknownContact() {
    final Reservation noSQLReservation = reservationService.create("first");

    final ServiceException serviceException =
      assertServiceException((Callable<Void>) () -> {
        reservationContactService.removeContactByExternalId(knownReservationPathContext(noSQLReservation.getId()), "UNKNOWN");
        return null;
      });

    MatcherAssert.assertThat(serviceException.getServiceMessage().getCode(), Matchers.is(ErrorMessages.CONTACT_NOT_FOUND.getCode()));
  }

  @Test
  public void deleteReservationContactWithoutAssignment() {
    final Contact contact = contactRepositoryService.create("deleteReservationContactWithoutAssignment");
    final Reservation noSQLReservation = reservationService.create("first");

    final ServiceException serviceException =
      assertServiceException((Callable<Void>) () -> {
        reservationContactService.removeContactByExternalId(knownReservationPathContext(noSQLReservation.getId()), contact.getExternalId());
        return null;
      });

    MatcherAssert.assertThat(serviceException.getServiceMessage().getCode(), Matchers.is(ErrorMessages.RESERVATION_CONTACT_NOT_FOUND
      .getCode()));
  }

  @Test
  public void deleteReservationContactWithAssignment() {
    when(restTemplate
      .getForEntity(anyString(), eq(ContactDetailsDTO.class)))
      .thenReturn(new ResponseEntity<>(response(), OK));
    final Contact contact = contactRepositoryService.create("deleteReservationContact");
    final Reservation noSQLReservation = reservationService.create("first");
    final Reservation reservation = reservationService.getReservationById(noSQLReservation.getId());
    reservationContactService.addContactByExternalId(knownReservationPathContext(reservation.getId()), contact.getId());

    reservationContactService.removeContactByExternalId(knownReservationPathContext(reservation.getId()), contact.getId());
    final List<ContactDetailsDTO> contacts =
      reservationContactService.getContactsOfAReservation(knownReservationPathContext(reservation.getId()));

    assertThat(contacts.size(), is(0));
  }

  private ReservationPathContext unknownReservationPathContext() {
    return new ReservationPathContext()
      .setApp("app")
      .setReservationId("unknown");
  }

  private ReservationPathContext knownReservationPathContext(final String reservationId) {
    return new ReservationPathContext()
      .setApp("app")
      .setReservationId(reservationId);
  }

  private ContactDetailsDTO response() {
    return new ContactDetailsDTO()
      .setMeta(new ContactDetailsMetaDTO().setType("individual"))
      .setId("Id")
      .setName("Name")
      .setModified("Modified")
      .setCreated("Created");
  }
}