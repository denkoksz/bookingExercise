package com.rs.service;

import java.time.LocalDate;
import java.util.List;

import com.rs.model.Asset;
import com.rs.model.TermQuantity;
import com.rs.model.ReservationAsset;
import com.rs.model.Term;

public interface ReservationAssetService {

  List<Asset> getAssets(String reservationId);

  Asset getAsset(String reservationId, String assetId);

  Asset addAsset(String reservationId, String assetId, Integer quantity, Term term);

  void deleteReservationAsset(String reservationId, String assetId);

  List<ReservationAsset> getOverLappingReservationAssets(Term term, String assetId, String reservationId);
}
