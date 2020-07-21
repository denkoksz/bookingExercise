package com.rs.api.reservations.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rs.api.ApiPathContext;
import com.rs.api.ContextConstants;
import com.rs.api.LinkBuilder;
import com.rs.api.MetaResponse;
import com.rs.api.reservations.ReservationsResponse;
import com.rs.api.reservations.ReservationsTranslator;
import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.api.reservations.reservation.ReservationResponse;
import com.rs.api.reservations.reservation.ReservationTranslator;
import com.rs.model.Reservation;
import com.rs.uris.Uris;

import static com.rs.api.ResponseConstants.FACETS;
import static com.rs.api.ResponseConstants.NEXT;
import static com.rs.api.ResponseConstants.SELF;
import static com.rs.api.ResponseConstants.SUGGESTIONS;

@Service
public class ReservationsTranslatorImpl implements ReservationsTranslator {

  private final ReservationTranslator reservationTranslator;

  public ReservationsTranslatorImpl(final ReservationTranslator reservationTranslator) {
    this.reservationTranslator = reservationTranslator;
  }

  @Override
  public ReservationsResponse translate(final ApiPathContext apiPathContext,
                                        final List<Reservation> reservations,
                                        final Integer total) {
    return ReservationsResponse.of(meta(apiPathContext, total),
      reservationResponses(apiPathContext, reservations), total);
  }

  private MetaResponse meta(final ApiPathContext apiPathContext, final long total) {
    final Integer offset = apiPathContext.getOffset();
    final Integer limit = apiPathContext.getLimit();
    return MetaResponse.empty()
      .addLink(NEXT, LinkBuilder.buildLink(Uris.PS.V1.RESERVATIONS.URI, apiPathContext) +
        "?" + ContextConstants.LIMIT + "=" + limit + "&" + ContextConstants.OFFSET + "=" + getNextOffset(total, offset, limit))
      .addLink(SELF, LinkBuilder.buildLink(Uris.PS.V1.RESERVATIONS.URI, apiPathContext) +
        "?" + ContextConstants.LIMIT + "=" + limit + "&" + ContextConstants.OFFSET + "=" + offset)
      .addLink(SUGGESTIONS, LinkBuilder.buildLink(Uris.PS.V1.SUGGESTIONS.URI, apiPathContext))
      .addLink(FACETS, LinkBuilder.buildLink(Uris.PS.V1.FACETS.URI, apiPathContext));
  }

  private int getNextOffset(final long total, final Integer offset, final Integer limit) {
    return total <= offset + limit ? offset : offset + limit;
  }

  private java.util.List<ReservationResponse> reservationResponses(final ApiPathContext apiPathContext,
                                                                   final List<Reservation> reservations) {
    return reservations.stream()
      .map(reservation ->
        reservationTranslator.translate(ReservationPathContext.of(
          apiPathContext.getApp(),
          reservation.getId()),
          reservation))
      .collect(Collectors.toList());
  }
}