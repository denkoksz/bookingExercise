package com.rs.api.reservations;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.api.AbstractApiSpec;
import com.rs.client.ApiException;
import com.rs.client.ApiResponse;
import com.rs.client.model.CreateReservationBody;
import com.rs.client.model.Reservation;
import com.rs.service.ReservationService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.http.HttpStatus.CREATED;

public class ReservationsControllerSpec extends AbstractApiSpec {

  @Autowired
  private ReservationService reservationService;

  @Before
  public void setupExistingAsset() {
    reservationService.create("first");
    reservationService.create("second");
    reservationService.create("third");
  }

  @Test
  public void create() throws ApiException {
    final CreateReservationBody createReservationBody = new CreateReservationBody();
    createReservationBody.setTitle("testTitle");
    createReservationBody.setType("testType");

    final ApiResponse<Reservation> reservation = createReservation(createReservationBody);

    assertThat(reservation.getStatusCode(), is(CREATED.value()));
    assertThat(reservation.getData().getId(), notNullValue());
  }
}
