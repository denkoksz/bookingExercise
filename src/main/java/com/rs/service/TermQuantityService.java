package com.rs.service;

import java.time.LocalDate;
import java.util.List;

import com.rs.model.Asset;
import com.rs.model.TermQuantity;

public interface TermQuantityService {

  List<TermQuantity> getFreeTermQuantities(LocalDate startDate, LocalDate endDate, String assetId);

}
