package com.rs.api.reservations.reservation;

import com.rs.model.Reservation;

public interface ReservationTranslator {

  ReservationResponse translate(ReservationPathContext pathContext, Reservation reservation);
}
