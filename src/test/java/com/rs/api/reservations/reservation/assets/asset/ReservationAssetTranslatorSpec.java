package com.rs.api.reservations.reservation.assets.asset;

import java.time.LocalDate;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.AbstractSpec;
import com.rs.model.Asset;
import com.rs.repository.service.AssetRepositoryService;

import static com.rs.api.ResponseConstants.ASSETS;
import static com.rs.api.ResponseConstants.RESERVATIONS;
import static com.rs.api.ResponseConstants.SELF;
import static com.rs.model.Asset.AssetType.ROOM;
import static com.rs.model.Asset.PartitionType.GROUP;
import static com.rs.model.EntityConsts.PREFIX_ASSET;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReservationAssetTranslatorSpec extends AbstractSpec {

  @Autowired
  private AssetRepositoryService assetRepositoryService;

  @Autowired
  private ReservationAssetTranslator reservationAssetTranslator;

  @Test
  public void meta() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, LocalDate.now().plusDays(5));
    final ReservationAssetResponse reservationAssetResponse = reservationAssetTranslator.translate(getParams(), asset);

    MatcherAssert.assertThat(reservationAssetResponse.getMeta().getLink(ASSETS), is("/api/v1/app/reservations/reservationId/assets"));
    MatcherAssert.assertThat(reservationAssetResponse.getMeta().getLink(SELF), is
      ("/api/v1/app/reservations/reservationId/assets/assetId"));
    MatcherAssert.assertThat(reservationAssetResponse.getMeta().getLink(RESERVATIONS), is("/api/v1/app/assets/assetId/reservations"));
  }

  @Test
  public void translate() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, LocalDate.now().plusDays(5));
    final ReservationAssetResponse reservationAssetResponse = reservationAssetTranslator.translate(getParams(), asset);

    assertThat(reservationAssetResponse.getId(), is(asset.getId()));
  }

  private ReservationAssetPathContext getParams() {
    return new ReservationAssetPathContext()
      .setApp("app")
      .setReservationId("reservationId")
      .setAssetId("assetId");
  }
}
