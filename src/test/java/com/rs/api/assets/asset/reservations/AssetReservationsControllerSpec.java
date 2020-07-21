package com.rs.api.assets.asset.reservations;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.api.AbstractApiSpec;
import com.rs.client.ApiException;
import com.rs.client.ApiResponse;
import com.rs.client.model.AssetReservations;
import com.rs.client.model.ReservationServiceError;
import com.rs.message.ErrorMessages;
import com.rs.model.Asset;
import com.rs.model.Reservation;
import com.rs.model.Term;
import com.rs.repository.service.AssetRepositoryService;
import com.rs.repository.service.ReservationAssetRepositoryService;
import com.rs.repository.service.ReservationRepositoryService;
import com.rs.service.IdGeneratorService;
import com.rs.service.ReservationService;

import static com.rs.model.Asset.AssetType.APPARTEMENT;
import static com.rs.model.Asset.PartitionType.GROUP;
import static com.rs.model.EntityConsts.PREFIX_ASSET;
import static java.time.LocalDate.now;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class AssetReservationsControllerSpec extends AbstractApiSpec {

  @Autowired
  private AssetRepositoryService assetService;

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ReservationRepositoryService reservationRepositoryService;

  @Autowired
  private ReservationAssetRepositoryService reservationAssetService;

  @Autowired
  private IdGeneratorService idGeneratorService;

  private Asset assetWithoutReservation;
  private Asset assetWithReservation;
  private Term term;


  @Before
  public void setupExistingAsset() {
    assetWithoutReservation = createAsset();
    assetWithReservation = createAsset();
    term = new Term().setStartDate(now()).setEndDate(now().plusDays(2));
    final Reservation reservation1 = reservationService.create("first");
    final Reservation reservation2 = reservationService.create("second");
    reservationAssetService.create(reservationRepositoryService.getById(reservation1.getId()), assetWithReservation, 1, term);
    reservationAssetService.create(reservationRepositoryService.getById(reservation2.getId()), assetWithReservation, 1, term);
  }

  @Test
  public void nonExistentAsset() {
    final ReservationServiceError ex = assertApiException(NOT_FOUND, () -> getReservationsOfAnAsset("AA-NotThere", null, null));

    assertThat(ex.getErrorCode(), Is.is(ErrorMessages.ASSET_NOT_FOUND.getCode()));
  }

  @Test
  public void assetWithNoReservations() throws ApiException {
    final ApiResponse<AssetReservations> resp = getReservationsOfAnAsset(assetWithoutReservation.getId(), null, null);

    assertThat(resp.getStatusCode(), is(SC_OK));
    assertThat(resp.getData().getItems().size(), is(0));
  }

  @Test
  public void AssetWithReservations() throws ApiException {
    final ApiResponse<AssetReservations> resp = getReservationsOfAnAsset(assetWithReservation.getId(), null, null);

    assertThat(resp.getStatusCode(), is(SC_OK));
    assertThat(resp.getData().getItems().size(), is(2));
  }

  private Asset createAsset() {
    return assetService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, APPARTEMENT, 1, now().plusDays(1));
  }
}
