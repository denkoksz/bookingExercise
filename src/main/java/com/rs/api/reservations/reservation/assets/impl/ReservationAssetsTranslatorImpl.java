package com.rs.api.reservations.reservation.assets.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rs.api.ContextConstants;
import com.rs.api.LinkBuilder;
import com.rs.api.MetaResponse;
import com.rs.api.reservations.reservation.assets.ReservationAssetsResponse;
import com.rs.api.reservations.reservation.assets.ReservationAssetsTranslator;
import com.rs.api.reservations.reservation.assets.asset.ReservationAssetPathContext;
import com.rs.api.reservations.reservation.assets.asset.ReservationAssetResponse;
import com.rs.api.reservations.reservation.assets.asset.ReservationAssetTranslator;
import com.rs.model.Asset;
import com.rs.uris.Uris;

import static com.rs.api.ResponseConstants.NEXT;
import static com.rs.api.ResponseConstants.SELF;

@Service
public class ReservationAssetsTranslatorImpl implements ReservationAssetsTranslator {

  private final ReservationAssetTranslator reservationAssetTranslator;

  public ReservationAssetsTranslatorImpl(ReservationAssetTranslator reservationAssetTranslator) {
    this.reservationAssetTranslator = reservationAssetTranslator;
  }

  @Override
  public ReservationAssetsResponse translate(final ReservationAssetsPathContext reservationAssetsPathContext, final List<Asset> assets) {
    return ReservationAssetsResponse.of(meta(reservationAssetsPathContext, assets.size()),
      reservationAssetResponses(reservationAssetsPathContext, assets),
      reservationAssetsPathContext.getLimit(),
      reservationAssetsPathContext.getOffset());
  }

  private MetaResponse meta(final ReservationAssetsPathContext reservationAssetsPathContext, final long total) {
    final Integer offset = reservationAssetsPathContext.getOffset();
    final Integer limit = reservationAssetsPathContext.getLimit();
    final long nextOffset = total <= offset + limit ? offset : offset + limit;
    return MetaResponse.empty()
      .addLink(SELF, LinkBuilder.buildLink(Uris.PS.V1.RESERVATIONS.RESERVATION.ASSETS.URI, reservationAssetsPathContext) +
        "?" + ContextConstants.LIMIT + "=" + limit + "&" + ContextConstants.OFFSET + "=" + offset)
      .addLink(NEXT, LinkBuilder.buildLink(Uris.PS.V1.RESERVATIONS.RESERVATION.ASSETS.URI, reservationAssetsPathContext) +
        "?" + ContextConstants.LIMIT + "=" + limit + "&" + ContextConstants.OFFSET + "=" + nextOffset);
  }

  private List<ReservationAssetResponse> reservationAssetResponses(final ReservationAssetsPathContext reservationAssetsPathContext,
                                                                   final List<Asset> assets) {
    return assets.stream().map(asset ->
      reservationAssetTranslator.translate(ReservationAssetPathContext.of(reservationAssetsPathContext, asset.getId()), asset))
      .collect(Collectors.toList());
  }
}