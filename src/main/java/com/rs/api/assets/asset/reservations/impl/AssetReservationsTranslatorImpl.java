package com.rs.api.assets.asset.reservations.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rs.api.MetaResponse;
import com.rs.api.assets.asset.AssetPathContext;
import com.rs.api.assets.asset.reservations.AssetReservationsResponse;
import com.rs.api.assets.asset.reservations.AssetReservationsTranslator;
import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.api.reservations.reservation.ReservationResponse;
import com.rs.api.reservations.reservation.ReservationTranslator;
import com.rs.model.Reservation;
import com.rs.service.ReservationService;
import com.rs.uris.Uris.PS.V1.ASSETS.ASSET.RESERVATIONS;

import static com.rs.api.LinkBuilder.buildLink;
import static com.rs.api.ResponseConstants.SELF;

@Service
public class AssetReservationsTranslatorImpl implements AssetReservationsTranslator {

  private final ReservationTranslator reservationTranslator;

  private final ReservationService reservationService;

  public AssetReservationsTranslatorImpl(final ReservationTranslator reservationTranslator, final ReservationService reservationService) {
    this.reservationTranslator = reservationTranslator;
    this.reservationService = reservationService;
  }

  @Override
  public AssetReservationsResponse translate(final AssetPathContext patchContext, final List<Reservation> reservations) {
    return AssetReservationsResponse.of(meta(patchContext), reservationResponses(patchContext, reservations));
  }

  private MetaResponse meta(final AssetPathContext pathContext) {
    return MetaResponse.empty().addLink(SELF, buildLink(RESERVATIONS.URI, pathContext));
  }

  private List<ReservationResponse> reservationResponses(final AssetPathContext pathContext, final List<Reservation> reservations) {
    return reservations.stream()
      .map(reservation -> reservationService.getReservationById(reservation.getId()))
      .map(reservation -> reservationTranslator.translate(
        ReservationPathContext.of(pathContext.getApp(), reservation.getId()), reservation))
      .collect(Collectors.toList());
  }
}
