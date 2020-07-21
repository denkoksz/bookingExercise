package com.rs.api.reservations.reservation.contacts.contact;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.api.AbstractApiSpec;
import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.client.ApiException;
import com.rs.client.ApiResponse;
import com.rs.client.model.ReservationServiceError;
import com.rs.model.Contact;
import com.rs.model.Reservation;
import com.rs.repository.service.ContactRepositoryService;
import com.rs.repository.service.ReservationContactRepositoryService;
import com.rs.service.ReservationService;

import static com.rs.message.ErrorMessages.CONTACT_NOT_FOUND;
import static com.rs.message.ErrorMessages.RESERVATION_CONTACT_NOT_FOUND;
import static com.rs.message.ErrorMessages.RESERVATION_NOT_FOUND;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

public class ReservationContactControllerSpec extends AbstractApiSpec {

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ContactRepositoryService contactRepositoryService;

  @Autowired
  private ReservationContactRepositoryService reservationContactRepositoryService;

  @Test
  public void deleteReservationContactUnknownContact() {
    final Reservation reservationForDelete = reservationService.create("first");

    final ReservationServiceError reservationServiceError =
      assertApiException(NOT_FOUND, () -> deleteReservationContact(reservationForDelete.getId(), "UNKNOWN"));

    assertThat(reservationServiceError.getErrorCode(), is(CONTACT_NOT_FOUND.getCode()));
  }

  @Test
  public void deleteReservationContactUnknownReservation() {
    final Contact contactForDelete = contactRepositoryService.create("AnyContact");
    final ReservationServiceError reservationServiceError =
      assertApiException(NOT_FOUND, () -> deleteReservationContact("UNKNOWN", contactForDelete.getExternalId()));

    assertThat(reservationServiceError.getErrorCode(), is(RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void deleteReservationContactWithoutAssignment() {
    final Reservation reservationForDelete = reservationService.create("first");
    final Contact notAssignedContact = contactRepositoryService.create("NotAssignedContact");

    final ReservationServiceError reservationServiceError =
      assertApiException(NOT_FOUND, () -> deleteReservationContact(reservationForDelete.getId(), notAssignedContact.getExternalId()));

    assertThat(reservationServiceError.getErrorCode(), is(RESERVATION_CONTACT_NOT_FOUND.getCode()));
  }

  @Test
  public void deleteReservationContactWithAssignment() throws ApiException {
    final Contact contactForDelete = contactRepositoryService.create("ContactForDelete");
    final Reservation reservationForDelete = reservationService.create("first");
    reservationContactRepositoryService.create(reservationService.getReservationById(reservationForDelete.getId()), contactForDelete);

    final ApiResponse<Void> response = deleteReservationContact(reservationForDelete.getId(), contactForDelete.getExternalId());

    assertThat(response.getStatusCode(), is(OK.value()));
  }

  private ReservationPathContext pathContext(final String reservationId) {
    return new ReservationPathContext()
      .setApp(APP)
      .setReservationId(reservationId);
  }
}