package com.rs.api.reservations.reservation.assets.asset;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.api.AbstractApiSpec;
import com.rs.client.ApiException;
import com.rs.client.ApiResponse;
import com.rs.client.model.ReservationAsset;
import com.rs.client.model.ReservationServiceError;
import com.rs.message.ErrorMessages;
import com.rs.model.Asset;
import com.rs.model.Reservation;
import com.rs.model.Term;
import com.rs.repository.service.AssetRepositoryService;
import com.rs.repository.service.ReservationAssetRepositoryService;
import com.rs.repository.service.ReservationRepositoryService;
import com.rs.uris.Uris;

import static com.rs.api.LinkBuilder.buildLink;
import static com.rs.model.Asset.AssetType.ROOM;
import static com.rs.model.Asset.PartitionType.GROUP;
import static com.rs.model.EntityConsts.PREFIX_ASSET;
import static java.time.LocalDate.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

public class ReservationAssetControllerSpec extends AbstractApiSpec {

  private Asset assignedAsset;
  private Asset notAssignedAsset;
  private Reservation reservation;

  private Asset assetForDelete;
  private Reservation reservationForDelete;

  @Autowired
  private ReservationRepositoryService reservationRepositoryService;

  @Autowired
  private AssetRepositoryService assetRepositoryService;

  @Autowired
  private ReservationAssetRepositoryService reservationAssetRepositoryService;

  private Term term;

  @Before
  public void createReservationAndAsset() {
    LocalDate endDate = now().plusDays(5);
    term = new Term().setStartDate(now()).setEndDate(now().plusDays(2));
    reservation = reservationRepositoryService.create("first");
    assignedAsset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, endDate);
    notAssignedAsset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, endDate);
    reservationAssetRepositoryService.create(reservation, assignedAsset, 2, term);
    assetForDelete = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, endDate);
    reservationForDelete = reservationRepositoryService.create("second");
    reservationAssetRepositoryService.create(reservationForDelete, assetForDelete, 2, term);
  }

  @Test
  public void getReservationAssetUnknownBoth() {
    final ReservationServiceError reservationServiceError =
      assertApiException(NOT_FOUND, () -> getReservationAsset("UNKNOWN", "UNKNOWN"));

    assertThat(reservationServiceError.getErrorCode(), is(ErrorMessages.RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void getReservationAssetUnknownReservation() {
    final ReservationServiceError reservationServiceError =
      assertApiException(NOT_FOUND, () -> getReservationAsset("UNKNOWN", assignedAsset.getId()));

    assertThat(reservationServiceError.getErrorCode(), is(ErrorMessages.RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void getReservationAssetUnknownAsset() {
    final ReservationServiceError reservationServiceError =
      assertApiException(NOT_FOUND, () -> getReservationAsset(reservation.getId(), "UNKNOWN"));

    assertThat(reservationServiceError.getErrorCode(), is(ErrorMessages.ASSET_NOT_FOUND.getCode()));
  }

  @Test
  public void getReservationAssetWithoutAssignment() {
    final ReservationServiceError reservationServiceError =
      assertApiException(NOT_FOUND, () -> getReservationAsset(reservation.getId(), notAssignedAsset.getId()));

    assertThat(reservationServiceError.getErrorCode(), is(ErrorMessages.RESERVATION_ASSET_NOT_FOUND.getCode()));
  }

  @Test
  public void getReservationAssetWithAssignment() throws ApiException {
    final ApiResponse<ReservationAsset> reservationAsset = getReservationAsset(reservation.getId(), assignedAsset.getId());

    assertThat(reservationAsset.getStatusCode(), is(OK.value()));
    assertThat(reservationAsset.getData().getId(), is(assignedAsset.getId()));
    assertThat(reservationAsset.getData().getMeta().getLinks().getAssets().getHref(),
      is(buildLink(Uris.PS.V1.RESERVATIONS.RESERVATION.ASSETS.URI, getParams())));
    assertThat(reservationAsset.getData().getMeta().getLinks().getSelf().getHref(),
      is(buildLink(Uris.PS.V1.RESERVATIONS.RESERVATION.ASSETS.ASSET.URI, getParams())));
    assertThat(reservationAsset.getData().getMeta().getLinks().getReservations().getHref(),
      is(buildLink(Uris.PS.V1.ASSETS.ASSET.RESERVATIONS.URI, getParams())));
  }

  @Test
  public void deleteReservationAssetUnknownAsset() {
    final ReservationServiceError reservationServiceError =
      assertApiException(NOT_FOUND, () -> deleteReservationAsset(reservationForDelete.getId(), "UNKNOWN"));

    assertThat(reservationServiceError.getErrorCode(), is(ErrorMessages.ASSET_NOT_FOUND.getCode()));
  }

  @Test
  public void deleteReservationAssetUnknownReservation() {
    final ReservationServiceError reservationServiceError =
      assertApiException(NOT_FOUND, () -> deleteReservationAsset("UNKNOWN", assetForDelete.getId()));

    assertThat(reservationServiceError.getErrorCode(), is(ErrorMessages.RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void deleteReservationAssetWithoutAssignment() {
    final ReservationServiceError reservationServiceError =
      assertApiException(NOT_FOUND, () -> deleteReservationAsset(reservationForDelete.getId(), notAssignedAsset.getId()));

    assertThat(reservationServiceError.getErrorCode(), is(ErrorMessages.RESERVATION_ASSET_NOT_FOUND.getCode()));
  }

  @Test
  public void deleteReservationAssetWithAssignment() throws ApiException {
    final ApiResponse<Void> response = deleteReservationAsset(reservationForDelete.getId(), assetForDelete.getId());

    assertThat(response.getStatusCode(), is(OK.value()));
  }

  private ReservationAssetPathContext getParams() {
    return new ReservationAssetPathContext()
      .setApp(APP)
      .setReservationId(reservation.getId())
      .setAssetId(assignedAsset.getId());
  }
}
