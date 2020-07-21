package com.rs.api.assets.asset.reservations;

import java.util.List;

import com.rs.api.assets.asset.AssetPathContext;
import com.rs.model.Reservation;

public interface AssetReservationsTranslator {

  AssetReservationsResponse translate(final AssetPathContext pathParameters,
                                      final List<Reservation> reservations);
}
