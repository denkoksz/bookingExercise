package com.rs.repository.custom;

import java.util.List;

import com.rs.model.Reservation;

public interface ReservationCustomRepository {

  List<Reservation> getReservations(String assetId);
}
