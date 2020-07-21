package com.rs.api.reservations.reservation.assets;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.AbstractSpec;
import com.rs.api.reservations.reservation.assets.asset.ReservationAssetResponse;
import com.rs.api.reservations.reservation.assets.impl.ReservationAssetsPathContext;
import com.rs.model.Asset;
import com.rs.model.Reservation;
import com.rs.model.Term;
import com.rs.repository.service.AssetRepositoryService;
import com.rs.repository.service.ReservationAssetRepositoryService;
import com.rs.repository.service.ReservationRepositoryService;

import static com.rs.api.ResponseConstants.NEXT;
import static com.rs.api.ResponseConstants.SELF;
import static com.rs.model.Asset.AssetType.ROOM;
import static com.rs.model.Asset.PartitionType.GROUP;
import static com.rs.model.EntityConsts.PREFIX_ASSET;
import static java.time.LocalDate.now;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ReservationAssetsTranslatorSpec extends AbstractSpec {

  @Autowired
  private ReservationAssetsTranslator reservationAssetsTranslator;

  @Autowired
  private ReservationRepositoryService reservationService;

  @Autowired
  private AssetRepositoryService assetRepositoryService;

  @Autowired
  private ReservationAssetRepositoryService reservationAssetRepositoryService;

  @Test
  public void meta() {
    final ReservationAssetsResponse translation = reservationAssetsTranslator.translate(getParams(), Collections.emptyList());

    assertThat(translation.getMeta().getLink(SELF), is("/api/v1/app/reservations/reservationId/assets?limit=2&offset=0"));
    assertThat(translation.getMeta().getLink(NEXT), is("/api/v1/app/reservations/reservationId/assets?limit=2&offset=0"));
  }

  @Test
  public void emptyItems() {
    final ReservationAssetsResponse translation = reservationAssetsTranslator.translate(getParams(), Collections.emptyList());

    assertThat(translation.getItems().size(), is(0));
    assertThat(translation.getTotal(), is(0));
  }

  @Test
  public void withItems() {
    final Term term = new Term().setStartDate(now()).setEndDate(now().plusDays(2));
    final Reservation reservation = reservationService.create("first");

    final Asset asset1 = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, now().plusDays(5));
    reservationAssetRepositoryService.create(reservation, asset1, 1, term);
    final Asset asset2 = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, now().plusDays(5));
    reservationAssetRepositoryService.create(reservation, asset2, 1, term);
    final ReservationAssetsResponse translation = reservationAssetsTranslator.translate(getParams(), List.of(asset1, asset2));
    final java.util.List<ReservationAssetResponse> items = translation.getItems();

    assertThat(items.size(), is(2));
    assertThat(translation.getTotal(), is(2));
    assertThat(items.get(0).getId(), is(asset1.getId()));
    assertThat(items.get(1).getId(), is(asset2.getId()));
  }

  private ReservationAssetsPathContext getParams() {
    return new ReservationAssetsPathContext()
      .setApp("app")
      .setLimit("2")
      .setOffset("0")
      .setReservationId("reservationId");
  }
}
