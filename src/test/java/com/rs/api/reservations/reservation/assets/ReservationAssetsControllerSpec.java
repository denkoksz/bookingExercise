package com.rs.api.reservations.reservation.assets;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.api.AbstractApiSpec;
import com.rs.client.ApiException;
import com.rs.client.ApiResponse;
import com.rs.client.model.AddAssetBody;
import com.rs.client.model.ReservationAssets;
import com.rs.client.model.ReservationServiceError;
import com.rs.message.ErrorMessages;
import com.rs.model.Asset;
import com.rs.model.Reservation;
import com.rs.model.ReservationAsset;
import com.rs.model.Term;
import com.rs.repository.service.AssetRepositoryService;
import com.rs.repository.service.ReservationAssetRepositoryService;
import com.rs.repository.service.ReservationRepositoryService;

import static com.rs.message.ErrorMessages.DATE_FORMAT_ERROR;
import static com.rs.message.ErrorMessages.DATE_NULL;
import static com.rs.message.ErrorMessages.RESERVATION_ASSET_END_AFTER_ASSET_END_DATE;
import static com.rs.message.ErrorMessages.RESERVATION_ASSET_QUANTITY_NEGATIVE;
import static com.rs.message.ErrorMessages.RESERVATION_ASSET_QUANTITY_NULL;
import static com.rs.message.ErrorMessages.RESERVATION_ASSET_QUANTITY_OVERFLOW;
import static com.rs.message.ErrorMessages.START_AFTER_END;
import static com.rs.message.ErrorMessages.START_DATE_IN_PAST;
import static com.rs.message.ErrorMessages.RESERVATION_NOT_FOUND;
import static com.rs.message.ErrorMessages.RESERVATION_TO_ASSET_OVERFLOW;
import static com.rs.model.Asset.AssetType.APPARTEMENT;
import static com.rs.model.Asset.AssetType.ROOM;
import static com.rs.model.Asset.AssetType.TENT;
import static com.rs.model.Asset.PartitionType.GROUP;
import static com.rs.model.Asset.PartitionType.OTHER;
import static com.rs.model.Asset.PartitionType.SINGLE;
import static com.rs.model.EntityConsts.PREFIX_ASSET;
import static java.time.LocalDate.now;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ReservationAssetsControllerSpec extends AbstractApiSpec {

  @Autowired
  private AssetRepositoryService assetService;

  @Autowired
  private ReservationRepositoryService reservationService;

  @Autowired
  private ReservationAssetRepositoryService reservationAssetService;

  private Asset assetToAddReservation1;
  private Asset assetToAddReservation2;
  private Asset assetToAddReservationTwice;
  private Reservation reservationWithAsset;
  private Reservation reservationWithoutAsset;
  private Reservation reservationToAddAsset1;
  private Reservation reservationToAddAsset2;
  private Term term;

  @Before
  public void setupExistingAsset() {
    LocalDate assetEndDate = now().plusYears(1);
    term = new Term().setStartDate(now()).setEndDate(now().plusDays(2));
    Asset assetWithReservation = assetService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 5, assetEndDate);
    assetToAddReservation1 = assetService.create(idGeneratorService.getId(PREFIX_ASSET), SINGLE, APPARTEMENT, 5, assetEndDate);
    assetToAddReservation2 = assetService.create(idGeneratorService.getId(PREFIX_ASSET), OTHER, TENT, 5, assetEndDate);
    assetToAddReservationTwice = assetService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 5, assetEndDate);
    reservationWithAsset = reservationService.create("first");
    reservationWithoutAsset = reservationService.create("second");
    reservationToAddAsset1 = reservationService.create("third");
    reservationToAddAsset2 = reservationService.create("fourth");
    reservationAssetService.create(reservationWithAsset, assetWithReservation, 1, term);
  }

  @Test
  public void nonExistentReservation() {
    final ReservationServiceError ex = assertApiException(NOT_FOUND, () -> getReservationAssets("PP-NotThere", 2, 0));

    assertThat(ex.getErrorCode(), is(RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void reservationWithoutAsset() throws ApiException {
    final ApiResponse<ReservationAssets> resp = getReservationAssets(reservationWithoutAsset.getId(), 2, 0);

    assertThat(resp.getStatusCode(), is(SC_OK));
    assertThat(resp.getData().getItems().size(), is(0));
  }

  @Test
  public void reservationWitAsset() throws ApiException {
    final ApiResponse<ReservationAssets> resp = getReservationAssets(reservationWithAsset.getId(), 2, 0);

    assertThat(resp.getStatusCode(), is(SC_OK));
    assertThat(resp.getData().getItems().size(), is(1));
  }

  @Test
  public void addAssetReservationAssetUnknownBoth() {
    final String startDate = now().plusDays(20).toString();
    final String endDate = now().plusDays(25).toString();
    final AddAssetBody addAssetBody = new AddAssetBody();
    addAssetBody.setId("UNKNOWN");
    addAssetBody.setStartDate(startDate);
    addAssetBody.setEndDate(endDate);

    final ReservationServiceError reservationServiceError =
      assertApiException(NOT_FOUND, () -> addAsset("UNKNOWN", addAssetBody));

    assertThat(reservationServiceError.getErrorCode(), is(ErrorMessages.RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void addAssetReservationUnknown() {
    final String startDate = now().plusDays(20).toString();
    final String endDate = now().plusDays(25).toString();
    final AddAssetBody addAssetBody = new AddAssetBody();

    addAssetBody.setId(assetToAddReservation1.getId());
    addAssetBody.setStartDate(startDate);
    addAssetBody.setEndDate(endDate);

    final ReservationServiceError reservationServiceError =
      assertApiException(NOT_FOUND, () -> addAsset("UNKNOWN", addAssetBody));

    assertThat(reservationServiceError.getErrorCode(), is(ErrorMessages.RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void addAssetAssetUnknownBoth() {
    final String startDate = now().plusDays(20).toString();
    final String endDate = now().plusDays(25).toString();
    final AddAssetBody addAssetBody = new AddAssetBody();

    addAssetBody.setId("UNKNOWN");
    addAssetBody.setQuantity(1);
    addAssetBody.setStartDate(startDate);
    addAssetBody.setEndDate(endDate);

    final ReservationServiceError reservationServiceError =
      assertApiException(NOT_FOUND, () -> addAsset(reservationToAddAsset1.getId(), addAssetBody));

    assertThat(reservationServiceError.getErrorCode(), is(ErrorMessages.ASSET_NOT_FOUND.getCode()));
  }

  @Test
  public void addAssetsCheckQuantity() {
    final String startDate = now().plusDays(20).toString();
    final String endDate = now().plusDays(25).toString();
    final AddAssetBody addAssetBody = new AddAssetBody();
    addAssetBody.setId(assetToAddReservation1.getId());
    addAssetBody.setQuantity(null);
    addAssetBody.setStartDate(startDate);
    addAssetBody.setEndDate(endDate);

    ReservationServiceError reservationServiceError = assertApiException(NOT_FOUND, () -> addAsset(reservationToAddAsset1.getId(),
      addAssetBody));

    assertThat(reservationServiceError.getErrorCode(), is(RESERVATION_ASSET_QUANTITY_NULL.getCode()));

    addAssetBody.setQuantity(-1);

    reservationServiceError = assertApiException(NOT_FOUND, () -> addAsset(reservationToAddAsset1.getId(), addAssetBody));

    assertThat(reservationServiceError.getErrorCode(), is(RESERVATION_ASSET_QUANTITY_NEGATIVE.getCode()));

    addAssetBody.setQuantity(assetToAddReservation1.getQuantity() + 1);

    reservationServiceError = assertApiException(NOT_FOUND, () -> addAsset(reservationToAddAsset1.getId(), addAssetBody));

    assertThat(reservationServiceError.getErrorCode(), is(RESERVATION_ASSET_QUANTITY_OVERFLOW.getCode()));
  }

  @Test
  public void addAssetsCheckTerm() {
    final String startDate = now().plusDays(20).toString();
    final String endDate = now().plusDays(25).toString();
    final AddAssetBody addAssetBody = new AddAssetBody();
    addAssetBody.setId(assetToAddReservation1.getId());
    addAssetBody.setQuantity(1);
    addAssetBody.setStartDate(startDate);

    ReservationServiceError reservationServiceError = assertApiException(BAD_REQUEST, () -> addAsset(reservationToAddAsset1.getId(),
      addAssetBody));

    assertThat(reservationServiceError.getErrorCode(), is(DATE_NULL.getCode()));

    addAssetBody.setStartDate(null);
    addAssetBody.setEndDate(endDate);

    reservationServiceError = assertApiException(BAD_REQUEST, () -> addAsset(reservationToAddAsset1.getId(),
      addAssetBody));

    assertThat(reservationServiceError.getErrorCode(), is(DATE_NULL.getCode()));

    addAssetBody.setStartDate(now().minusDays(20).toString());
    addAssetBody.setEndDate(endDate);

    reservationServiceError = assertApiException(BAD_REQUEST, () -> addAsset(reservationToAddAsset1.getId(),
      addAssetBody));

    assertThat(reservationServiceError.getErrorCode(), is(START_DATE_IN_PAST.getCode()));

    addAssetBody.setStartDate(now().plusDays(30).toString());
    addAssetBody.setEndDate(endDate);

    reservationServiceError = assertApiException(BAD_REQUEST, () -> addAsset(reservationToAddAsset1.getId(),
      addAssetBody));

    assertThat(reservationServiceError.getErrorCode(), is(START_AFTER_END.getCode()));

    addAssetBody.setStartDate(now().plusDays(30).toString());
    addAssetBody.setEndDate(now().plusYears(1).plusDays(1).toString());

    reservationServiceError = assertApiException(BAD_REQUEST, () -> addAsset(reservationToAddAsset1.getId(),
      addAssetBody));

    assertThat(reservationServiceError.getErrorCode(), is(RESERVATION_ASSET_END_AFTER_ASSET_END_DATE.getCode()));
  }

  @Test
  public void addAssets() throws ApiException {
    final String startDate = now().plusDays(20).toString();
    final String endDate = now().plusDays(25).toString();
    final AddAssetBody addAssetBody = new AddAssetBody();
    addAssetBody.setId(assetToAddReservation1.getId());
    addAssetBody.setQuantity(1);
    addAssetBody.setStartDate(startDate);
    addAssetBody.setEndDate(endDate);

    ApiResponse<Void> apiResponse = addAsset(reservationToAddAsset1.getId(), addAssetBody);

    assertThat(apiResponse.getStatusCode(), is(CREATED.value()));
    ReservationAsset reservationAsset = reservationAssetService.getByReservationAsset(reservationToAddAsset1, assetToAddReservation1);
    assertThat(reservationAsset.getQuantity(), is(1));
    assertThat(reservationAsset.getStartDate(), is(LocalDate.parse(startDate)));
    assertThat(reservationAsset.getEndDate(), is(LocalDate.parse(endDate)));

    addAssetBody.setId(assetToAddReservation2.getId());
    addAssetBody.setQuantity(1);

    apiResponse = addAsset(reservationToAddAsset1.getId(), addAssetBody);
    assertThat(apiResponse.getStatusCode(), is(CREATED.value()));
    reservationAsset = reservationAssetService.getByReservationAsset(reservationToAddAsset1, assetToAddReservation2);
    assertThat(reservationAsset.getQuantity(), is(1));
  }

  @Test
  public void addAssetTwice() throws ApiException {
    final AddAssetBody addAssetBody = new AddAssetBody();
    addAssetBody.setId(assetToAddReservationTwice.getId());
    addAssetBody.setQuantity(1);
    addAssetBody.setStartDate(now().plusDays(20).toString());
    addAssetBody.setEndDate(now().plusDays(25).toString());

    ApiResponse<Void> apiResponse = addAsset(reservationToAddAsset1.getId(), addAssetBody);

    assertThat(apiResponse.getStatusCode(), is(CREATED.value()));
    ReservationAsset reservationAsset = reservationAssetService.getByReservationAsset(reservationToAddAsset1, assetToAddReservationTwice);
    assertThat(reservationAsset.getQuantity(), is(1));
    assertThat(reservationAsset.getStartDate(), is(LocalDate.parse(now().plusDays(20).toString())));
    assertThat(reservationAsset.getEndDate(), is(LocalDate.parse(now().plusDays(25).toString())));

    addAssetBody.setQuantity(2);
    addAssetBody.setStartDate(now().plusDays(21).toString());
    addAssetBody.setEndDate(now().plusDays(26).toString());
    apiResponse = addAsset(reservationToAddAsset1.getId(), addAssetBody);

    assertThat(apiResponse.getStatusCode(), is(CREATED.value()));
    reservationAsset = reservationAssetService.getByReservationAsset(reservationToAddAsset1, assetToAddReservationTwice);
    assertThat(reservationAsset.getQuantity(), is(2));
    assertThat(reservationAsset.getStartDate(), is(LocalDate.parse(now().plusDays(21).toString())));
    assertThat(reservationAsset.getEndDate(), is(LocalDate.parse(now().plusDays(26).toString())));
  }

  @Test
  public void addAssetReservationOverflow() throws ApiException {
    final AddAssetBody addAssetBody = new AddAssetBody();
    addAssetBody.setId(assetToAddReservationTwice.getId());
    addAssetBody.setQuantity(3);
    addAssetBody.setStartDate(now().plusDays(20).toString());
    addAssetBody.setEndDate(now().plusDays(25).toString());

    ApiResponse<Void> apiResponse = addAsset(reservationToAddAsset1.getId(), addAssetBody);

    assertThat(apiResponse.getStatusCode(), is(CREATED.value()));
    ReservationAsset reservationAsset = reservationAssetService.getByReservationAsset(reservationToAddAsset1, assetToAddReservationTwice);
    assertThat(reservationAsset.getQuantity(), is(3));
    assertThat(reservationAsset.getStartDate(), is(LocalDate.parse(now().plusDays(20).toString())));
    assertThat(reservationAsset.getEndDate(), is(LocalDate.parse(now().plusDays(25).toString())));

    addAssetBody.setQuantity(3);
    addAssetBody.setStartDate(now().plusDays(21).toString());
    addAssetBody.setEndDate(now().plusDays(26).toString());

    final ReservationServiceError reservationServiceError =
      assertApiException(BAD_REQUEST, ()->addAsset(reservationToAddAsset2.getId(), addAssetBody));

    assertThat(reservationServiceError.getErrorCode(), is(RESERVATION_TO_ASSET_OVERFLOW.getCode()));
  }

  @Test
  public void parseDate() {
    final String startDate = "2020/10/10";
    final String endDate = "2020/11/10";
    final AddAssetBody addAssetBody = new AddAssetBody();
    addAssetBody.setId(assetToAddReservationTwice.getId());
    addAssetBody.setQuantity(1);
    addAssetBody.setStartDate(startDate);
    addAssetBody.setEndDate(endDate);

    ReservationServiceError reservationServiceError = assertApiException(BAD_REQUEST, () -> addAsset(reservationToAddAsset1.getId(),
      addAssetBody));
    assertThat(reservationServiceError.getErrorCode(), is(DATE_FORMAT_ERROR.getCode()));
  }
}
