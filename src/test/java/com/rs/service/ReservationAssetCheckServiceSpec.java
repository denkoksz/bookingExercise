package com.rs.service;

import java.time.LocalDate;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.api.exception.ServiceException;
import com.rs.model.Asset;
import com.rs.model.Term;
import com.rs.repository.service.AssetRepositoryService;

import static com.rs.message.ErrorMessages.RESERVATION_ASSET_END_AFTER_ASSET_END_DATE;
import static com.rs.message.ErrorMessages.RESERVATION_ASSET_QUANTITY_NEGATIVE;
import static com.rs.message.ErrorMessages.RESERVATION_ASSET_QUANTITY_NULL;
import static com.rs.message.ErrorMessages.RESERVATION_ASSET_QUANTITY_OVERFLOW;
import static com.rs.message.ErrorMessages.START_AFTER_END;
import static com.rs.message.ErrorMessages.START_DATE_IN_PAST;
import static com.rs.model.Asset.AssetType.ROOM;
import static com.rs.model.Asset.PartitionType.GROUP;
import static java.time.LocalDate.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReservationAssetCheckServiceSpec extends AbstractServiceSpec {

  @Autowired
  private AssetRepositoryService assetRepositoryService;

  @Autowired
  private ReservationAssetCheckService reservationAssetCheckService;

  @Test
  public void quantityIsNull() {
    final Asset asset = assetRepositoryService.create("Hotel 1", GROUP, ROOM, 10, now().plusDays(5));
    final Integer quantity = null;

    ServiceException serviceException = assertServiceException(() -> reservationAssetCheckService.checkQuantity(asset, quantity));

    assertThat(serviceException.getServiceMessage().getCode(), is(RESERVATION_ASSET_QUANTITY_NULL.getCode()));
  }

  @Test
  public void quantityIsNegative() {
    final Asset asset = assetRepositoryService.create("Hotel 1", GROUP, ROOM, 10, now().plusDays(5));
    final Integer quantity = -5;

    ServiceException serviceException = assertServiceException(() -> reservationAssetCheckService.checkQuantity(asset, quantity));

    assertThat(serviceException.getServiceMessage().getCode(), is(RESERVATION_ASSET_QUANTITY_NEGATIVE.getCode()));
  }

  @Test
  public void quantityIsGreaterThanAsset() {
    final Asset asset = assetRepositoryService.create("Hotel 1", GROUP, ROOM, 10, now().plusDays(5));
    final Integer quantity = 15;

    ServiceException serviceException = assertServiceException(() -> reservationAssetCheckService.checkQuantity(asset, quantity));

    assertThat(serviceException.getServiceMessage().getCode(), is(RESERVATION_ASSET_QUANTITY_OVERFLOW.getCode()));
  }

  @Test
  public void quantityIsValid() {
    final Asset asset = assetRepositoryService.create("Hotel 1", GROUP, ROOM, 10, now().plusDays(5));
    final Integer quantity = 5;

    assertThat(reservationAssetCheckService.checkQuantity(asset, quantity), is(5));
  }

  @Test
  public void termStartDateInPast() {
    final Asset asset = assetRepositoryService.create("Hotel 1", GROUP, ROOM, 10, now().plusYears(1));
    LocalDate reservationStartDate = now().minusDays(1);
    LocalDate reservationEndDate = now().plusDays(1);
    Term term = new Term(reservationStartDate, reservationEndDate);

    ServiceException serviceException =
      assertServiceException(() -> reservationAssetCheckService.checkTerm(asset, term));

    assertThat(serviceException.getServiceMessage().getCode(), is(START_DATE_IN_PAST.getCode()));
  }

  @Test
  public void termStartDateAfterEndDate() {
    final Asset asset = assetRepositoryService.create("Hotel 1", GROUP, ROOM, 10, now().plusYears(1));
    LocalDate reservationStartDate = now().plusDays(2);
    LocalDate reservationEndDate = now().plusDays(1);

    Term term = new Term(reservationStartDate, reservationEndDate);

    ServiceException serviceException =
      assertServiceException(() -> reservationAssetCheckService.checkTerm(asset, term));

    assertThat(serviceException.getServiceMessage().getCode(), is(START_AFTER_END.getCode()));
  }

  @Test
  public void termAfterAssetEndDate() {
    final Asset asset = assetRepositoryService.create("Hotel 1", GROUP, ROOM, 10, now());

    LocalDate reservationStartDate = now().plusDays(2);
    LocalDate reservationEndDate = now().plusDays(3);

    Term term = new Term(reservationStartDate, reservationEndDate);

    ServiceException serviceException =
      assertServiceException(() -> reservationAssetCheckService.checkTerm(asset, term));

    assertThat(serviceException.getServiceMessage().getCode(), is(RESERVATION_ASSET_END_AFTER_ASSET_END_DATE.getCode()));
  }

  @Test
  public void termIsValid() {
    final Asset asset = assetRepositoryService.create("Hotel 1", GROUP, ROOM, 10, now().plusYears(1));

    LocalDate reservationStartDate = now().plusDays(2);
    LocalDate reservationEndDate = now().plusDays(3);

    Term term = new Term(reservationStartDate, reservationEndDate);

    assertThat(reservationAssetCheckService.checkTerm(asset, term), is(term));
  }
}
