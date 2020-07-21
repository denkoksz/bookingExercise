package com.rs.api.assets.asset.reservations;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rs.api.assets.asset.AssetPathContext;
import com.rs.model.Reservation;
import com.rs.repository.service.ReservationRepositoryService;
import com.rs.uris.Uris.PS.V1.ASSETS.ASSET.RESERVATIONS;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class AssetReservationsController {

  private final ReservationRepositoryService reservationRepositoryService;

  private final AssetReservationsTranslator assetReservationsTranslator;

  public AssetReservationsController(final ReservationRepositoryService reservationRepositoryService,
                                     final AssetReservationsTranslator assetReservationsTranslator) {
    this.reservationRepositoryService = reservationRepositoryService;
    this.assetReservationsTranslator = assetReservationsTranslator;
  }

  @RequestMapping(path = RESERVATIONS.URI,
    method = GET,
    produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(OK)
  public AssetReservationsResponse getReservationsAssetAssignedFor(final AssetPathContext pathParameters) {
    final List<Reservation> reservations = reservationRepositoryService.getReservations(pathParameters.getAssetId());
    return assetReservationsTranslator.translate(pathParameters, reservations);
  }
}
