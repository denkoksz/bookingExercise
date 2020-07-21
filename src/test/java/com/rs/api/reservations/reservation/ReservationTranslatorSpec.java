package com.rs.api.reservations.reservation;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.api.ContextConstants;
import com.rs.service.AbstractServiceSpec;
import com.rs.service.ReservationService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class ReservationTranslatorSpec extends AbstractServiceSpec {

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ReservationTranslator reservationTranslator;

  @Test
  public void translate() {
    final ReservationResponse reservationResponse = reservationTranslator.translate(getContext(), reservationService.create("first"));

    assertThat(reservationResponse.getId(), notNullValue());
  }

  private ReservationPathContext getContext() {
    return new ReservationPathContext()
      .setApp(ContextConstants.APP)
      .setReservationId("reservationId");
  }
}
