package com.rs.api.assets.asset.reservations;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.AbstractSpec;
import com.rs.api.assets.asset.AssetPathContext;
import com.rs.api.reservations.reservation.ReservationResponse;
import com.rs.model.Reservation;
import com.rs.repository.service.ReservationRepositoryService;
import com.rs.service.ReservationService;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AssetReservationsTranslatorSpec extends AbstractSpec {

  @Autowired
  private AssetReservationsTranslator assetReservationsTranslator;

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ReservationRepositoryService reservationRepositoryService;

  @Test
  public void meta() {
    final AssetReservationsResponse translation = assetReservationsTranslator.translate(getParams(), Collections.emptyList());

    assertThat(translation.getMeta().getLink("self"), is("/api/v1/app/assets/assetId/reservations"));
  }

  @Test
  public void emptyItems() {
    final AssetReservationsResponse translation = assetReservationsTranslator.translate(getParams(), Collections.emptyList());

    assertThat(translation.getItems().size(), is(0));
    assertThat(translation.getTotal(), is(0));
  }

  @Test
  public void withItems() {
    final Reservation reservation1 = reservationService.create("first");
    final Reservation reservation2 = reservationService.create("second");

    final AssetReservationsResponse translation =
      assetReservationsTranslator.translate(getParams(), List.of(reservationRepositoryService.getById(reservation1.getId()),
        reservationRepositoryService.getById(reservation2.getId())));
    final java.util.List<ReservationResponse> items = translation.getItems();

    assertThat(items.size(), is(2));
    assertThat(translation.getTotal(), is(2));
    assertThat(items.get(0).getId(), is(reservation1.getId()));
    assertThat(items.get(1).getId(), is(reservation2.getId()));
  }

  private AssetPathContext getParams() {
    return new AssetPathContext()
      .setApp("app")
      .setAssetId("assetId");
  }
}
