package com.rs.api.reservations.reservation.assets.asset;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rs.model.Asset;
import com.rs.service.ReservationAssetService;
import com.rs.uris.Uris;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ReservationAssetController {

  private final ReservationAssetService reservationAssetService;

  private final ReservationAssetTranslator reservationAssetTranslator;

  public ReservationAssetController(final ReservationAssetService reservationAssetService,
                                    final ReservationAssetTranslator reservationAssetTranslator) {
    this.reservationAssetService = reservationAssetService;
    this.reservationAssetTranslator = reservationAssetTranslator;
  }

  @RequestMapping(path = Uris.PS.V1.RESERVATIONS.RESERVATION.ASSETS.ASSET.URI,
    method = GET,
    produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(OK)
  @Transactional
  public ReservationAssetResponse getAsset(final ReservationAssetPathContext pathContext) {
    final Asset asset = reservationAssetService.getAsset(pathContext.getReservationId(), pathContext.getAssetId());
    return reservationAssetTranslator.translate(pathContext, asset);
  }

  @RequestMapping(path = Uris.PS.V1.RESERVATIONS.RESERVATION.ASSETS.ASSET.URI,
    method = DELETE,
    consumes = APPLICATION_JSON_VALUE,
    produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(OK)
  @Transactional
  public void deleteAsset(final ReservationAssetPathContext reservationAssetPathContext) {
    reservationAssetService.deleteReservationAsset(reservationAssetPathContext.getReservationId(),
      reservationAssetPathContext.getAssetId());
  }
}
