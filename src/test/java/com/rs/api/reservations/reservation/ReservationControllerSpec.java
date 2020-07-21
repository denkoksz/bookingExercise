package com.rs.api.reservations.reservation;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.rs.api.AbstractApiSpec;
import com.rs.client.ApiException;
import com.rs.client.ApiResponse;
import com.rs.client.model.CreateReservationBody;
import com.rs.client.model.Reservation;
import com.rs.client.model.ReservationServiceError;
import com.rs.uris.Uris.PS;

import static com.rs.api.LinkBuilder.buildLink;
import static com.rs.message.ErrorMessages.RESERVATION_NOT_FOUND;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.http.HttpStatus.OK;

public class ReservationControllerSpec extends AbstractApiSpec {

  @Test
  public void getKnown() throws ApiException {
    final CreateReservationBody createReservationBody = new CreateReservationBody();
    createReservationBody.setTitle("testTitle");
    createReservationBody.setType("testType");
    final ApiResponse<Reservation> createdReservation = createReservation(createReservationBody);
    final ReservationPathContext reservationPathContext = getContext(createdReservation);

    final ApiResponse<Reservation> getReservation = getReservation(createdReservation.getData().getId(), "");

    assertThat(getReservation.getStatusCode(), is(OK.value()));
    assertThat(getReservation.getData().getId(), is(getReservation.getData().getId()));
    assertThat(getReservation.getData().getMeta().getLinks().getAssets().getHref(),
      is(buildLink(PS.V1.RESERVATIONS.RESERVATION.ASSETS.URI, reservationPathContext)));
    assertThat(getReservation.getData().getMeta().getLinks().getContacts().getHref(),
      is(buildLink(PS.V1.RESERVATIONS.RESERVATION.CONTACTS.URI, reservationPathContext)));
    assertThat(getReservation.getData().getMeta().getLinks().getDocuments().getHref(),
      is(buildLink(PS.V1.RESERVATIONS.RESERVATION.DOCUMENTS.URI, reservationPathContext)));
    assertThat(getReservation.getData().getMeta().getLinks().getReservations().getHref(),
      is(buildLink(PS.V1.RESERVATIONS.URI, reservationPathContext)));
    assertThat(getReservation.getData().getMeta().getLinks().getSelf().getHref(),
      is(buildLink(PS.V1.RESERVATIONS.RESERVATION.URI, reservationPathContext)));
  }

  @Test
  public void getUnknown() {
    final ReservationServiceError reservationServiceError =
      assertApiException(HttpStatus.NOT_FOUND, () -> getReservation("UNKNOWN", ""));
    assertThat(reservationServiceError, notNullValue());
    assertThat(reservationServiceError.getErrorCode(), is(RESERVATION_NOT_FOUND.getCode()));
  }

  private ReservationPathContext getContext(final ApiResponse<Reservation> createdReservation) {
    return new ReservationPathContext()
      .setApp(APP)
      .setReservationId(createdReservation.getData().getId());
  }
}
