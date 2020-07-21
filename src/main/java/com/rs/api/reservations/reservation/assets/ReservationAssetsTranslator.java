package com.rs.api.reservations.reservation.assets;

import java.util.List;

import com.rs.api.reservations.reservation.assets.impl.ReservationAssetsPathContext;
import com.rs.model.Asset;

public interface ReservationAssetsTranslator {

  ReservationAssetsResponse translate(final ReservationAssetsPathContext reservationAssetsPathContext, final List<Asset> assets);
}
