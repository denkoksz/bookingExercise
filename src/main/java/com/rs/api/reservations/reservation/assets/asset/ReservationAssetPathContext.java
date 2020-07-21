package com.rs.api.reservations.reservation.assets.asset;

import com.rs.api.BasePathContext;
import com.rs.api.ContextConstants;
import com.rs.api.reservations.reservation.assets.impl.ReservationAssetsPathContext;

public class ReservationAssetPathContext extends BasePathContext<ReservationAssetPathContext> {

  public ReservationAssetPathContext setReservationId(final String reservationId) {
    super.addParam(ContextConstants.VAR_RESERVATION_ID, reservationId);
    return this;
  }

  public ReservationAssetPathContext setAssetId(final String assetId) {
    super.addParam(ContextConstants.VAR_ASSET_ID, assetId);
    return this;
  }

  public String getReservationId() {
    return getParam(ContextConstants.VAR_RESERVATION_ID);
  }

  public String getAssetId() {
    return getParam(ContextConstants.VAR_ASSET_ID);
  }

  public static ReservationAssetPathContext of(final ReservationAssetsPathContext reservationAssetsPathContext, final String assetId) {
    return new ReservationAssetPathContext()
      .setApp(reservationAssetsPathContext.getApp())
      .setReservationId(reservationAssetsPathContext.getReservationId())
      .setAssetId(assetId);
  }
}
