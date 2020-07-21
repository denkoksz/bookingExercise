package com.rs.repository.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.api.exception.ServiceException;
import com.rs.model.Asset;
import com.rs.model.Reservation;
import com.rs.model.ReservationAsset;
import com.rs.model.Term;
import com.rs.service.AbstractServiceSpec;

import static com.rs.message.ErrorMessages.RESERVATION_ASSET_NOT_FOUND;
import static com.rs.model.Asset.AssetType.ROOM;
import static com.rs.model.Asset.PartitionType.GROUP;
import static com.rs.model.EntityConsts.PREFIX_ASSET;
import static java.time.LocalDate.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReservationAssetRepositoryServiceSpec extends AbstractServiceSpec {

  @Autowired
  private ReservationRepositoryService reservationRepositoryService;

  @Autowired
  private AssetRepositoryService assetRepositoryService;

  @Autowired
  private ReservationAssetRepositoryService reservationAssetRepositoryService;

  private Term term = new Term().setStartDate(now()).setEndDate(now().plusDays(2));

  @Test
  public void create() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, now().plusDays(5));
    final Reservation reservation = reservationRepositoryService.create("first");

    final ReservationAsset reservationAsset = reservationAssetRepositoryService.create(reservation, asset, 1, term);

    assertThat(reservationAsset.getAssetId(), is(asset.getId()));
    assertThat(reservationAsset.getReservationId(), is(reservation.getId()));
  }

  @Test
  public void getByReservationAssetWithAssignment() {

    final Term term = new Term().setStartDate(now()).setEndDate(now().plusDays(2));
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, now().plusDays(5));
    final Reservation reservation = reservationRepositoryService.create("first");
    final ReservationAsset createdReservationAsset = reservationAssetRepositoryService.create(reservation, asset, 1, term);
    final ReservationAsset reservationAsset = reservationAssetRepositoryService.getByReservationAsset(reservation, asset);

    assertThat(reservationAsset, is(createdReservationAsset));
    assertThat(reservationAsset.getAssetId(), is(asset.getId()));
    assertThat(reservationAsset.getReservationId(), is(reservation.getId()));
  }

  @Test
  public void getByReservationAssetWithoutAssignment() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, now().plusDays(5));
    final Reservation reservation = reservationRepositoryService.create("first");

    final ServiceException serviceException =
      assertServiceException(() -> reservationAssetRepositoryService.getByReservationAsset(reservation, asset));

    assertThat(serviceException.getServiceMessage(), is(RESERVATION_ASSET_NOT_FOUND));
  }

  @Test
  public void delete() {
    final Term term = new Term().setStartDate(now()).setEndDate(now().plusDays(2));
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, now().plusDays(5));
    final Reservation reservation = reservationRepositoryService.create("first");
    final ReservationAsset reservationAsset = reservationAssetRepositoryService.create(reservation, asset, 1, term);

    reservationAssetRepositoryService.delete(reservationAsset);

    final List<Asset> assets = assetRepositoryService.getAssets(reservation.getId());
    assertThat(assets.size(), is(0));
  }
}
