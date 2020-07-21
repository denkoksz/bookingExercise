package com.rs.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rs.model.Asset;
import com.rs.model.DateQuantity;
import com.rs.model.TermQuantity;
import com.rs.model.ReservationAsset;
import com.rs.model.Term;
import com.rs.repository.AssetRepository;
import com.rs.repository.ReservationAssetRepository;
import com.rs.repository.service.AssetRepositoryService;
import com.rs.service.ReservationAssetCheckService;
import com.rs.service.TermQuantityService;

import static java.lang.Math.min;
import static java.time.LocalDate.now;

@Service
public class TermQuantityServiceImpl implements TermQuantityService {

  private final ReservationAssetRepository reservationAssetRepository;

  private final ReservationAssetCheckService checkService;

  private final AssetRepositoryService assetRepositoryService;

  public TermQuantityServiceImpl(final ReservationAssetRepository reservationAssetRepository,
                                 final ReservationAssetCheckService checkService,
                                 final AssetRepositoryService assetRepositoryService) {
    this.reservationAssetRepository = reservationAssetRepository;
    this.checkService = checkService;
    this.assetRepositoryService = assetRepositoryService;
  }

  @Override
  public List<TermQuantity> getFreeTermQuantities(final LocalDate startDate,
                                                  final LocalDate endDate,
                                                  final String assetId) {
    Asset asset = assetRepositoryService.getById(assetId);
    checkService.checkTerm(asset, new Term(startDate, endDate));
    final List<ReservationAsset> ras = reservationAssetRepository.getOverLappingReservationAssets(new Term(startDate, endDate),
      asset.getId(), "noReservation");

    List<DateQuantity> freeDateQuantities = getFreeDateQuantityPerDay(ras, startDate, endDate, asset);
    return getFreeTermQuantities(freeDateQuantities);
  }


  private List<TermQuantity> getFreeTermQuantities(List<DateQuantity> freeDateQuantities) {
    TermQuantity first = findFirst(freeDateQuantities);
    List<DateQuantity> subtractedList = subtract(first, freeDateQuantities);
    List<TermQuantity> freeTermQuantities = getFreeAssetsList(subtractedList);
    freeTermQuantities.add(first);
    return first.getQuantity() == 0 ? Collections.emptyList() : freeTermQuantities;
  }

  private List<TermQuantity> getFreeAssetsList(final List<DateQuantity> subtractedList) {
    if (subtractedList.size() > 0) {
      return getFreeTermQuantities(subtractedList);
    } else {
      return new ArrayList<>();
    }
  }

  private TermQuantity findFirst(final List<DateQuantity> freeDateQuantities) {
    final DateQuantity first = freeDateQuantities.stream().findFirst().orElse(new DateQuantity(now(), 0L));
    final TermQuantity freeTermQuantity = new TermQuantity(
      new Term(first.getDate(), first.getDate().plusDays(1)), first.getQuantity().intValue());
    freeDateQuantities.stream().skip(1)
      .filter(freeDateQuantity -> freeDateQuantity.getDate().equals(freeTermQuantity.getTerm().getEndDate()))
      .forEach(freeDateQuantity -> {
        freeTermQuantity.getTerm().setEndDate(freeDateQuantity.getDate().plusDays(1));
        freeTermQuantity.setQuantity(min(freeTermQuantity.getQuantity(), freeDateQuantity.getQuantity().intValue()));
      });
    return freeTermQuantity;
  }

  private List<DateQuantity> subtract(final TermQuantity toSubtract,
                                      final List<DateQuantity> freeDateQuantities) {
    return freeDateQuantities.stream().
      map(freeDateQuantity -> getSubtractedFreeDateQuantity(toSubtract, freeDateQuantity))
      .filter(freeDateQuantity -> freeDateQuantity.getQuantity() > 0)
      .collect(Collectors.toList());
  }

  private DateQuantity getSubtractedFreeDateQuantity(final TermQuantity toSubtract, final DateQuantity freeDateQuantity) {
    if (toSubtract.getTerm().contains(freeDateQuantity.getDate())) {
      return new DateQuantity(freeDateQuantity.getDate(),
        freeDateQuantity.getQuantity() - toSubtract.getQuantity());
    }
    return freeDateQuantity;
  }

  private List<DateQuantity> getFreeDateQuantityPerDay(final List<ReservationAsset> ras, final LocalDate startDate,
                                                       final LocalDate endDate, final Asset asset) {
    return startDate.datesUntil(endDate).collect(Collectors.toList()).stream()
      .map(date -> new DateQuantity(date,
        asset.getQuantity() - checkService.getSumQuantityPerDay(ras, date)))
      .filter(freeDateQuantity -> freeDateQuantity.getQuantity() > 0)
      .collect(Collectors.toList());
  }

}
