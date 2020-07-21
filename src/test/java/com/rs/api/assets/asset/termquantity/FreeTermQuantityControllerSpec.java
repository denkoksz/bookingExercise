package com.rs.api.assets.asset.termquantity;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.api.AbstractApiSpec;
import com.rs.client.ApiException;
import com.rs.client.ApiResponse;
import com.rs.client.model.FreeTermQuantities;
import com.rs.client.model.ReservationServiceError;
import com.rs.model.Asset;
import com.rs.model.Reservation;
import com.rs.model.Term;
import com.rs.repository.service.AssetRepositoryService;
import com.rs.repository.service.ReservationAssetRepositoryService;
import com.rs.repository.service.ReservationRepositoryService;
import com.rs.service.IdGeneratorService;
import com.rs.service.ReservationService;

import static com.rs.message.ErrorMessages.DATE_FORMAT_ERROR;
import static com.rs.message.ErrorMessages.START_AFTER_END;
import static com.rs.message.ErrorMessages.START_DATE_IN_PAST;
import static com.rs.model.Asset.AssetType.APPARTEMENT;
import static com.rs.model.Asset.PartitionType.GROUP;
import static com.rs.model.EntityConsts.PREFIX_ASSET;
import static java.time.LocalDate.now;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class FreeTermQuantityControllerSpec extends AbstractApiSpec {

  @Autowired
  private AssetRepositoryService assetService;

  @Autowired
  private IdGeneratorService idGeneratorService;

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ReservationAssetRepositoryService reservationAssetService;

  @Autowired
  private ReservationRepositoryService reservationRepositoryService;

  private Asset asset;

  @Before
  public void setupExistingAsset() {
    asset = createAsset();
    final Term term = new Term().setStartDate(now()).setEndDate(now().plusDays(2));
    final Reservation reservation = reservationService.create("first");
    reservationAssetService.create(reservationRepositoryService.getById(reservation.getId()), asset, 1, term);
  }

  @Test
  public void happyPath() throws ApiException {
    ApiResponse<FreeTermQuantities> resp = getFreeTermQuantities(now().toString(), now().plusDays(2).toString(),
      asset.getId());

    assertThat(resp.getStatusCode(), is(SC_OK));
  }

  @Test
  public void wrongDateFormat() {
    final String startDate = "2020/10/10";
    final String endDate = "2020/11/10";

    ReservationServiceError reservationServiceError = assertApiException(BAD_REQUEST, () -> getFreeTermQuantities(startDate, endDate,
      asset.getId()));

    assertThat(reservationServiceError.getErrorCode(), is(DATE_FORMAT_ERROR.getCode()));
  }

  @Test
  public void startInPast() {
    ReservationServiceError reservationServiceError = assertApiException(BAD_REQUEST,
      () -> getFreeTermQuantities(now().minusDays(1).toString(), now().toString(), asset.getId()));

    assertThat(reservationServiceError.getErrorCode(), is(START_DATE_IN_PAST.getCode()));
  }

  @Test
  public void startAfterEnd() {
    ReservationServiceError reservationServiceError = assertApiException(BAD_REQUEST,
      () -> getFreeTermQuantities(now().plusDays(1).toString(), now().toString(), asset.getId()));

    assertThat(reservationServiceError.getErrorCode(), is(START_AFTER_END.getCode()));
  }

  private Asset createAsset() {
    return assetService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, APPARTEMENT, 5, now().plusYears(1));
  }
}
