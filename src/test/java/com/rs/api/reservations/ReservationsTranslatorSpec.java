package com.rs.api.reservations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.AbstractSpec;
import com.rs.api.ApiPathContext;
import com.rs.model.Reservation;
import com.rs.repository.service.ReservationRepositoryService;

import static com.rs.api.ResponseConstants.NEXT;
import static com.rs.api.ResponseConstants.SELF;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ReservationsTranslatorSpec extends AbstractSpec {

  @Autowired
  private ReservationsTranslator reservationsTranslator;

  @Autowired
  private ReservationRepositoryService reservationService;

  @Test
  public void meta() {
    final ReservationsResponse translation = reservationsTranslator.translate(getParams(), Collections.emptyList(), 1);

    assertThat(translation.getMeta().getLink(SELF), is("/api/v1/app/reservations?limit=2&offset=0"));
    assertThat(translation.getMeta().getLink(NEXT), is("/api/v1/app/reservations?limit=2&offset=0"));
  }

  @Test
  public void emptyItems() {
    final ReservationsResponse translation = reservationsTranslator.translate(getParams(), Collections.emptyList(), 0);

    assertThat(translation.getReservations().size(), is(0));
    assertThat(translation.getTotal(), is(0));
  }

  @Test
  public void withItems() {
    List<Reservation> reservations = new ArrayList<>();
    reservations.add(reservationService.create("first"));
    reservations.add(reservationService.create("second"));

    final ReservationsResponse translation =
      reservationsTranslator.translate(getParams(), reservations, 2);

    assertThat(translation.getReservations().size(), is(2));
    assertThat(translation.getTotal(), is(2));
  }

  private ApiPathContext getParams() {
    return new ApiPathContext()
      .setApp("app")
      .setLimit("2")
      .setOffset("0");
  }
}
