package com.rs.service;

import java.util.concurrent.Callable;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.api.exception.ServiceException;
import com.rs.message.ErrorMessages;
import com.rs.model.Asset;
import com.rs.model.Reservation;
import com.rs.model.Term;
import com.rs.repository.service.AssetRepositoryService;
import com.rs.repository.service.ReservationAssetRepositoryService;
import com.rs.repository.service.ReservationRepositoryService;

import static com.rs.model.Asset.AssetType.ROOM;
import static com.rs.model.Asset.PartitionType.GROUP;
import static com.rs.model.EntityConsts.PREFIX_ASSET;
import static java.time.LocalDate.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReservationAssetServiceSpec extends AbstractServiceSpec {

  @Autowired
  private ReservationRepositoryService reservationRepositoryService;

  @Autowired
  private AssetRepositoryService assetRepositoryService;

  @Autowired
  private ReservationAssetRepositoryService reservationAssetRepositoryService;

  @Autowired
  private ReservationAssetService reservationAssetService;

  private Term term = new Term().setStartDate(now()).setEndDate(now().plusDays(2));


  @Test
  public void getAssetUnknownReservation() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, now().plusDays(5));
    final ServiceException serviceException =
      assertServiceException(() -> reservationAssetService.getAsset("UNKNOWN", asset.getId()));

    MatcherAssert.assertThat(serviceException.getServiceMessage().getCode(), Matchers.is(ErrorMessages.RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void getAssetUnknownAsset() {
    final Reservation reservation = reservationRepositoryService.create("first");
    final ServiceException serviceException =
      assertServiceException(() -> reservationAssetService.getAsset(reservation.getId(), "UNKNOWN"));

    MatcherAssert.assertThat(serviceException.getServiceMessage().getCode(), Matchers.is(ErrorMessages.ASSET_NOT_FOUND.getCode()));
  }

  @Test
  public void getAssetWithoutAssignment() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, now().plusDays(5));
    final Reservation reservation = reservationRepositoryService.create("first");
    final ServiceException serviceException =
      assertServiceException(() -> reservationAssetService.getAsset(reservation.getId(), asset.getId()));

    MatcherAssert.assertThat(serviceException.getServiceMessage().getCode(), Matchers.is(ErrorMessages.RESERVATION_ASSET_NOT_FOUND.getCode
      ()));
  }

  @Test
  public void getAssetWithAssignment() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, now().plusDays(5));
    final Reservation reservation = reservationRepositoryService.create("first");
    reservationAssetRepositoryService.create(reservation, asset, 1, term);

    final Asset getAsset = reservationAssetService.getAsset(reservation.getId(), asset.getId());

    assertThat(getAsset.getId(), is(asset.getId()));
  }

  @Test
  public void addAssetUnknownReservation() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, now().plusDays(5));

    final ServiceException serviceException =
      assertServiceException(() -> reservationAssetService.addAsset("UNKNOWN", asset.getId(), 1, term));

    MatcherAssert.assertThat(serviceException.getServiceMessage().getCode(), Matchers.is(ErrorMessages.RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void addAssetUnknownAsset() {
    final Reservation reservation = reservationRepositoryService.create("first");
    final ServiceException serviceException =
      assertServiceException(() -> reservationAssetService.addAsset(reservation.getId(), "UNKNOWN", 1, term));

    MatcherAssert.assertThat(serviceException.getServiceMessage().getCode(), Matchers.is(ErrorMessages.ASSET_NOT_FOUND.getCode()));
  }

  @Test
  public void addAssetWithoutAssignment() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, now().plusDays(5));
    final Reservation reservation = reservationRepositoryService.create("first");
    final Asset assetAdded = reservationAssetService.addAsset(reservation.getId(), asset.getId(), 1, term);

    assertThat(assetAdded.getId(), is(asset.getId()));
  }

  @Test
  public void addAssetWithAssignment() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, now().plusDays(5));
    final Reservation reservation = reservationRepositoryService.create("first");
    reservationAssetService.addAsset(reservation.getId(), asset.getId(), 1, term);

    final Asset assetAddedSecondTime = reservationAssetService.addAsset(reservation.getId(), asset.getId(), 1, term);

    assertThat(assetAddedSecondTime.getId(), is(asset.getId()));
  }

  @Test
  public void deleteReservationAssetUnknownReservation() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, now().plusDays(5));

    final ServiceException serviceException =
      assertServiceException((Callable<Void>) () -> {
        reservationAssetService.deleteReservationAsset("UNKNOWN", asset.getId());
        return null;
      });

    MatcherAssert.assertThat(serviceException.getServiceMessage().getCode(), Matchers.is(ErrorMessages.RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void deleteReservationAssetUnknownAsset() {
    final Reservation reservation = reservationRepositoryService.create("first");

    final ServiceException serviceException =
      assertServiceException((Callable<Void>) () -> {
        reservationAssetService.deleteReservationAsset(reservation.getId(), "UNKNOWN");
        return null;
      });

    MatcherAssert.assertThat(serviceException.getServiceMessage().getCode(), Matchers.is(ErrorMessages.ASSET_NOT_FOUND.getCode()));
  }

  @Test
  public void deleteReservationAssetWithoutAssignment() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, now().plusDays(5));
    final Reservation reservation = reservationRepositoryService.create("first");

    final ServiceException serviceException =
      assertServiceException((Callable<Void>) () -> {
        reservationAssetService.deleteReservationAsset(reservation.getId(), asset.getId());
        return null;
      });

    MatcherAssert.assertThat(serviceException.getServiceMessage().getCode(), Matchers.is(ErrorMessages.RESERVATION_ASSET_NOT_FOUND.getCode
      ()));
  }

  @Test
  public void deleteReservationAssetWithAssignment() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, now().plusDays(5));
    final Reservation reservation = reservationRepositoryService.create("first");
    reservationAssetRepositoryService.create(reservation, asset, 1, term);
    reservationAssetService.deleteReservationAsset(reservation.getId(), asset.getId());
  }
}
