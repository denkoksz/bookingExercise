package com.rs.repository.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.api.exception.ServiceException;
import com.rs.model.Asset;
import com.rs.model.Reservation;
import com.rs.model.Term;
import com.rs.service.AbstractServiceSpec;

import static com.rs.message.ErrorMessages.ASSET_END_DATE_IN_PAST;
import static com.rs.message.ErrorMessages.ASSET_END_DATE_NULL;
import static com.rs.model.Asset.AssetType.APPARTEMENT;
import static com.rs.model.Asset.AssetType.ROOM;
import static com.rs.model.Asset.PartitionType.GROUP;
import static com.rs.model.Asset.PartitionType.SINGLE;
import static com.rs.model.EntityConsts.PREFIX_ASSET;
import static java.time.LocalDate.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AssetRepositoryServiceSpec extends AbstractServiceSpec {

  private final String ASSET_ID_FIRST = "AssetServiceSpec1";
  private final String ASSET_ID_SECOND = "AssetServiceSpec2";

  @Autowired
  private ReservationRepositoryService reservationRepositoryService;

  @Autowired
  private AssetRepositoryService assetRepositoryService;

  @Autowired
  private ReservationAssetRepositoryService reservationAssetRepositoryService;

  @Test
  public void create() {
    LocalDate assetEndDate = now().plusYears(1);
    final Asset first = assetRepositoryService.create(ASSET_ID_FIRST, GROUP, ROOM, 1, assetEndDate);
    final Asset second = assetRepositoryService.create(ASSET_ID_SECOND, GROUP, ROOM, 3, assetEndDate.plusDays(1));

    assertThat(first.getId(), is(ASSET_ID_FIRST));
    assertThat(first.getPartitionType(), is(GROUP));
    assertThat(first.getAssetType(), is(ROOM));
    assertThat(first.getQuantity(), is(1));
    assertThat(first.getEndDate(), is(assetEndDate));
    assertThat(second.getId(), is(ASSET_ID_SECOND));
    assertThat(second.getPartitionType(), is(GROUP));
    assertThat(second.getAssetType(), is(ROOM));
    assertThat(second.getQuantity(), is(3));
    assertThat(second.getEndDate(), is(assetEndDate.plusDays(1)));
  }

  @Test
  public void getById() {
    final Optional<Asset> getByIdFirstTry = assetRepositoryService.findById(ASSET_ID_FIRST);

    assertThat(getByIdFirstTry.isPresent(), is(false));

    final Asset first = assetRepositoryService.create(ASSET_ID_FIRST, SINGLE, ROOM, 1, now().plusDays(5));

    final Optional<Asset> getByIdSecondTry = assetRepositoryService.findById(ASSET_ID_FIRST);

    assertThat(getByIdSecondTry.get(), is(first));
  }

  @Test
  public void getAssets() {
    final Term term = new Term().setStartDate(now()).setEndDate(now().plusDays(2));
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), SINGLE, APPARTEMENT, 1, now().plusDays(5));
    final Reservation reservation = reservationRepositoryService.create("first");
    reservationAssetRepositoryService.create(reservation, asset, 1, term);

    final List<Asset> assets = assetRepositoryService.getAssets(reservation.getId());

    assertThat(assets.size(), is(1));
    assertThat(assets.get(0).getId(), is(asset.getId()));
  }

  @Test
  public void endDateNull() {
    final ServiceException serviceException =
      assertServiceException( () -> assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), SINGLE, APPARTEMENT, 1, null));

    assertThat(serviceException.getServiceMessage(), is(ASSET_END_DATE_NULL));
  }

  @Test
  public void endDateToday() {
    LocalDate assetEndDate = now();
    final Asset first = assetRepositoryService.create(ASSET_ID_FIRST, GROUP, ROOM, 1, assetEndDate);

    assertThat(first.getEndDate(), is(assetEndDate));
  }

  @Test
  public void endDateEarlierThanToday() {
    final ServiceException serviceException =
      assertServiceException( () -> assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), SINGLE, APPARTEMENT, 1, now().minusDays(5)));

    assertThat(serviceException.getServiceMessage(), is(ASSET_END_DATE_IN_PAST));
  }
}
