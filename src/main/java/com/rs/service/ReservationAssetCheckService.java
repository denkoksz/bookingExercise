package com.rs.service;

import java.time.LocalDate;
import java.util.List;

import com.rs.model.Asset;
import com.rs.model.ReservationAsset;
import com.rs.model.Term;

public interface ReservationAssetCheckService {

  Integer checkQuantity(Asset asset, Integer quantity);

  Term checkTerm(Asset asset, Term term);

  Asset checkAvailability(Term term, Integer quantity, Asset asset, String reservationId);

  long getSumQuantityPerDay(final List<ReservationAsset> ras, LocalDate date);

}


