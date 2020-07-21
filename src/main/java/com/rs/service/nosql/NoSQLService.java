package com.rs.service.nosql;

import com.rs.model.Reservation;
import com.rs.service.impl.NoSQLReservation;

public interface NoSQLService {

  String ID = "id";
  String CREATED = "created";
  String LAST_MODIFIED = "lastModified";

  NoSQLReservation index(final Reservation reservation);

  NoSQLReservation getById(final String reservationId);

  void clearIndex();
}
