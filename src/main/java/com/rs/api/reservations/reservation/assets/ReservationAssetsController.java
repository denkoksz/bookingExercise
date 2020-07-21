package com.rs.api.reservations.reservation.assets;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rs.api.reservations.reservation.assets.asset.ReservationAssetPathContext;
import com.rs.api.reservations.reservation.assets.asset.ReservationAssetResponse;
import com.rs.api.reservations.reservation.assets.asset.ReservationAssetTranslator;
import com.rs.api.reservations.reservation.assets.impl.ReservationAssetsPathContext;
import com.rs.model.Asset;
import com.rs.service.ReservationAssetService;
import com.rs.uris.Uris;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ReservationAssetsController {

  private final ReservationAssetService reservationAssetService;

  private final ReservationAssetTranslator reservationAssetTranslator;

  private final ReservationAssetsTranslator reservationAssetsTranslator;

  public ReservationAssetsController(final ReservationAssetService reservationAssetService,
                                     final ReservationAssetTranslator reservationAssetTranslator,
                                     final ReservationAssetsTranslator reservationAssetsTranslator) {
    this.reservationAssetService = reservationAssetService;
    this.reservationAssetTranslator = reservationAssetTranslator;
    this.reservationAssetsTranslator = reservationAssetsTranslator;
  }

  @RequestMapping(path = Uris.PS.V1.RESERVATIONS.RESERVATION.ASSETS.URI,
    method = GET,
    produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(OK)
  @Transactional
  public ReservationAssetsResponse getAssets(final ReservationAssetsPathContext reservationAssetsPathContext) {
    final List<Asset> assets = reservationAssetService.getAssets(reservationAssetsPathContext.getReservationId());
    return reservationAssetsTranslator.translate(reservationAssetsPathContext, assets);
  }

  @RequestMapping(path = Uris.PS.V1.RESERVATIONS.RESERVATION.ASSETS.URI,
    method = POST,
    consumes = APPLICATION_JSON_VALUE,
    produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(CREATED)
  @Transactional
  public ReservationAssetResponse addAsset(final ReservationAssetsPathContext reservationAssetsPathContext,
                                           @RequestBody final AddAssetRequest assetCreateRequest) {
    final Asset asset = reservationAssetService.addAsset(reservationAssetsPathContext.getReservationId(), assetCreateRequest.getId(),
      assetCreateRequest.getQuantity(), assetCreateRequest.getTerm());
    return reservationAssetTranslator.translate(ReservationAssetPathContext.of(reservationAssetsPathContext, asset.getId()), asset);
  }
}
