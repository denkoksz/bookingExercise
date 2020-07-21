package com.rs.service.nosql.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.rs.api.exception.ServiceException;
import com.rs.model.Reservation;
import com.rs.service.impl.NoSQLReservation;
import com.rs.service.nosql.NoSQLService;

import static com.rs.api.ContextConstants.VAR_RESERVATION_ID;
import static com.rs.message.ErrorMessages.RESERVATION_NOT_FOUND;

@Service
public class NoSQLServiceImpl implements NoSQLService {

  private java.util.HashMap<String, NoSQLReservation> noSQLReservations = new java.util.HashMap<>();
  private final Environment env;

  public NoSQLServiceImpl(final Environment env) {
    this.env = env;
  }

  @Override
  public NoSQLReservation index(final Reservation reservation) {
    final NoSQLReservation noSQLReservation = NoSQLReservation.of(reservation);
    noSQLReservations.put(reservation.getId(), noSQLReservation);
    return noSQLReservation;
  }

  @Override
  public NoSQLReservation getById(final String reservationId) {
    return Optional.ofNullable(noSQLReservations.get(reservationId))
      .orElseThrow(() -> new ServiceException(RESERVATION_NOT_FOUND, Optional.of(Map.of(VAR_RESERVATION_ID, reservationId))));
  }

  @Override
  public void clearIndex() {
    noSQLReservations.clear();
  }
}

