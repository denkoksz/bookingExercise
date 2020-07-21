package com.rs.repository.custom;

import java.util.List;
import java.util.Optional;

import com.rs.model.ReservationAsset;
import com.rs.model.Term;

public interface ReservationAssetCustomRepository {

  Optional<ReservationAsset> findByReservationIdAndAssetId(String reservationId, String assetId);

  List<ReservationAsset> getOverLappingReservationAssets(Term term, String assetId, String reservationId);
}
