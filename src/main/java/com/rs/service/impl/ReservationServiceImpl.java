package com.rs.service.impl;

import org.springframework.stereotype.Service;

import com.rs.model.Reservation;
import com.rs.repository.service.ReservationRepositoryService;
import com.rs.service.ReservationService;
import com.rs.service.nosql.NoSQLService;

@Service
public class ReservationServiceImpl implements ReservationService {

  private final ReservationRepositoryService reservationRepositoryService;

  private final NoSQLService noSQLService;

  public ReservationServiceImpl(final ReservationRepositoryService reservationRepositoryService, final NoSQLService noSQLService) {
    this.reservationRepositoryService = reservationRepositoryService;
    this.noSQLService = noSQLService;
  }

  @Override
  public Reservation create(String title) {
    final Reservation reservation = reservationRepositoryService.create(title);
    noSQLService.index(reservation);
    return reservation;
  }

  @Override
  public NoSQLReservation getNoSQLReservationById(final String reservationId) {
    final Reservation reservation = reservationRepositoryService.getById(reservationId);
    return noSQLService.getById(reservation.getId());
  }

  @Override
  public Reservation getReservationById(final String reservationId) {
    final NoSQLReservation reservation = noSQLService.getById(reservationId);
    return reservationRepositoryService.getById(reservation.getId());
  }
}
