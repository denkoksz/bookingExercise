package com.rs.api.reservations.reservation.contacts;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

import com.rs.api.AbstractApiSpec;
import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.api.reservations.reservation.contacts.contact.service.dto.ContactDetailsDTO;
import com.rs.api.reservations.reservation.contacts.contact.service.dto.ContactDetailsMetaDTO;
import com.rs.auth.Oauth2ClientConfig;
import com.rs.client.ApiException;
import com.rs.client.ApiResponse;
import com.rs.client.model.AddContactBody;
import com.rs.client.model.Contacts;
import com.rs.client.model.ReservationServiceError;
import com.rs.model.Reservation;
import com.rs.service.ReservationContactService;
import com.rs.service.ReservationService;

import static com.rs.message.ErrorMessages.RESERVATION_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

public class ReservationContactsControllerSpec extends AbstractApiSpec {

  @MockBean
  @Qualifier(Oauth2ClientConfig.CONTACT_CLIENT_CREDENTIALS_QUALIFIER)
  private OAuth2RestTemplate restTemplate;

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ReservationContactService reservationContactService;

  private Reservation reservationWithoutContact;

  private Reservation reservationWithContact;

  @Before
  public void createTestData() {
    when(restTemplate
      .getForEntity(anyString(),
        eq(ContactDetailsDTO.class)))
      .thenReturn(new ResponseEntity<>(response(), OK));
    reservationWithoutContact = reservationService.create("first");
    reservationWithContact = reservationService.create("second");
    reservationContactService.addContactByExternalId(ReservationPathContext.of(APP, reservationWithContact.getId()), "Id");
  }

  @Test
  public void testNonExistentReservation() {
    final ReservationServiceError ex = assertApiException(NOT_FOUND, () -> getReservationContacts("unknown"));

    assertThat(ex.getErrorCode(), is(RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void testReservationWithoutContact() throws ApiException {
    final ApiResponse<Contacts> resp = getReservationContacts(reservationWithoutContact.getId());
    final Contacts data = resp.getData();

    assertThat(resp.getStatusCode(), is(SC_OK));
    assertThat(data.getMeta().getLinks().getSelf().getHref(),
      is("/api/v1/basems/reservations/" + reservationWithoutContact.getId() + "/contacts"));
    assertThat(data.getItems().size(), is(0));
    assertThat(data.getTotal(), is(0));
  }

  @Test
  public void testReservationWithContact() throws ApiException {
    final ApiResponse<Contacts> resp = getReservationContacts(reservationWithContact.getId());
    final Contacts data = resp.getData();

    assertThat(resp.getStatusCode(), is(SC_OK));
    assertThat(data.getMeta().getLinks().getSelf().getHref(),
      is("/api/v1/basems/reservations/" + reservationWithContact.getId() + "/contacts"));
    assertThat(data.getItems().size(), is(1));
    assertThat(data.getTotal(), is(1));
  }

  @Test
  public void testContactNotFound() throws ApiException {

    final ApiResponse<Contacts> resp = getReservationContacts(reservationWithContact.getId());
    final Contacts data = resp.getData();

    assertThat(resp.getStatusCode(), is(SC_OK));
    assertThat(data.getMeta().getLinks().getSelf().getHref(),
      is("/api/v1/basems/reservations/" + reservationWithContact.getId() + "/contacts"));
    assertThat(data.getItems().size(), is(1));
    assertThat(data.getTotal(), is(1));
  }

  @Test
  public void testAddContact() throws ApiException {

    AddContactBody contactBody = new AddContactBody();
    contactBody.setId("Id");
    final ApiResponse<Void> resp = addReservationContacts(reservationWithContact.getId(), contactBody);

    assertThat(resp.getStatusCode(), is(SC_CREATED));
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