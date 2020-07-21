package com.rs.api.reservations;

import java.util.List;

import com.rs.api.ApiPathContext;
import com.rs.model.Reservation;

public interface ReservationsTranslator {

  ReservationsResponse translate(ApiPathContext apiPathContext,
                                 List<Reservation> reservations,
                                 Integer total);
}
