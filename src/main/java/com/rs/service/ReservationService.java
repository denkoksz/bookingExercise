package com.rs.service;

import com.rs.model.Reservation;
import com.rs.service.impl.NoSQLReservation;

public interface ReservationService {

  Reservation create(String title);

  NoSQLReservation getNoSQLReservationById(String reservationId);

  Reservation getReservationById(final String reservationId);
}
