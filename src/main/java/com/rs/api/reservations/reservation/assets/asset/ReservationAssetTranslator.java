package com.rs.api.reservations.reservation.assets.asset;

import com.rs.model.Asset;

public interface ReservationAssetTranslator {

  ReservationAssetResponse translate(final ReservationAssetPathContext reservationAssetPathContext, final Asset asset);
}
