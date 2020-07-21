package com.rs.repository.service;

import java.util.Optional;

import com.rs.model.Asset;
import com.rs.model.Reservation;
import com.rs.model.ReservationAsset;
import com.rs.model.Term;

public interface ReservationAssetRepositoryService {

  ReservationAsset create(Reservation reservation, Asset asset, Integer quantity, Term term);

  Optional<ReservationAsset> findByReservationAndAsset(Reservation reservation, Asset asset);

  ReservationAsset getByReservationAsset(Reservation reservation, Asset asset);

  ReservationAsset update(ReservationAsset reservationAsset, Integer quantity, Term term);

  void delete(ReservationAsset reservationAsset);
}
