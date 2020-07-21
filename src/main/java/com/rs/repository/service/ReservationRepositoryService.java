package com.rs.repository.service;

import java.util.List;

import com.rs.model.Reservation;

public interface ReservationRepositoryService {

  Reservation create(String title);

  Reservation getById(String id);

  List<Reservation> getReservations(String assetId);
}
