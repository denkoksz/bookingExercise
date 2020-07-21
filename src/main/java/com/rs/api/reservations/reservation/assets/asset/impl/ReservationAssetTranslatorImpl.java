package com.rs.api.reservations.reservation.assets.asset.impl;

import org.springframework.stereotype.Service;

import com.rs.api.LinkBuilder;
import com.rs.api.MetaResponse;
import com.rs.api.reservations.reservation.assets.asset.ReservationAssetPathContext;
import com.rs.api.reservations.reservation.assets.asset.ReservationAssetResponse;
import com.rs.api.reservations.reservation.assets.asset.ReservationAssetTranslator;
import com.rs.model.Asset;
import com.rs.uris.Uris;

import static com.rs.api.ResponseConstants.ASSETS;
import static com.rs.api.ResponseConstants.RESERVATIONS;
import static com.rs.api.ResponseConstants.SELF;

@Service
public class ReservationAssetTranslatorImpl implements ReservationAssetTranslator {

  @Override
  public ReservationAssetResponse translate(final ReservationAssetPathContext reservationAssetPathContext, final Asset asset) {
    return ReservationAssetResponse.of(meta(reservationAssetPathContext), asset.getId());
  }

  private MetaResponse meta(final ReservationAssetPathContext reservationAssetPathContext) {
    return MetaResponse.empty()
      .addLink(ASSETS, LinkBuilder.buildLink(Uris.PS.V1.RESERVATIONS.RESERVATION.ASSETS.URI, reservationAssetPathContext))
      .addLink(SELF, LinkBuilder.buildLink(Uris.PS.V1.RESERVATIONS.RESERVATION.ASSETS.ASSET.URI, reservationAssetPathContext))
      .addLink(RESERVATIONS, LinkBuilder.buildLink(Uris.PS.V1.ASSETS.ASSET.RESERVATIONS.URI, reservationAssetPathContext));
  }
}
