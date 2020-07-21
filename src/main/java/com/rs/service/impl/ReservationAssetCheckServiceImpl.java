package com.rs.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rs.api.exception.ServiceException;
import com.rs.model.Asset;
import com.rs.model.ReservationAsset;
import com.rs.model.Term;
import com.rs.repository.ReservationAssetRepository;
import com.rs.service.ReservationAssetCheckService;

import static com.rs.message.ErrorMessageConstants.ASSET_ID;
import static com.rs.message.ErrorMessages.RESERVATION_ASSET_END_AFTER_ASSET_END_DATE;
import static com.rs.message.ErrorMessages.RESERVATION_ASSET_QUANTITY_NEGATIVE;
import static com.rs.message.ErrorMessages.RESERVATION_ASSET_QUANTITY_NULL;
import static com.rs.message.ErrorMessages.RESERVATION_ASSET_QUANTITY_OVERFLOW;
import static com.rs.message.ErrorMessages.RESERVATION_TO_ASSET_OVERFLOW;
import static com.rs.message.ErrorMessages.START_AFTER_END;
import static com.rs.message.ErrorMessages.START_DATE_IN_PAST;
import static java.time.LocalDate.now;

@Service
public class ReservationAssetCheckServiceImpl implements ReservationAssetCheckService {

  private final ReservationAssetRepository reservationAssetRepository;

  public ReservationAssetCheckServiceImpl(final ReservationAssetRepository reservationAssetRepository) {
    this.reservationAssetRepository = reservationAssetRepository;
  }

  @Override
  public Integer checkQuantity(final Asset asset, final Integer quantity) {
    Optional.ofNullable(quantity)
      .or(() -> {
        throw new ServiceException(RESERVATION_ASSET_QUANTITY_NULL, Optional.empty());
      })
      .filter(value -> value > 0)
      .or(() -> {
        throw new ServiceException(RESERVATION_ASSET_QUANTITY_NEGATIVE, Optional.empty());
      })
      .filter(value -> value <= asset.getQuantity())
      .or(() -> {
        throw new ServiceException(RESERVATION_ASSET_QUANTITY_OVERFLOW, Optional.of(Map.of(ASSET_ID, asset.getId())));
      });
    return quantity;
  }

  @Override
  public Term checkTerm(final Asset asset, final Term term) {
    Optional.of(term)
      .filter(t -> t.getStartDate().isEqual(now()) || t.getStartDate().isAfter(now()))
      .or(() -> {
        throw new ServiceException(START_DATE_IN_PAST, Optional.empty());
      })
      .filter(t -> t.getStartDate().isBefore(t.getEndDate()))
      .or(() -> {
        throw new ServiceException(START_AFTER_END, Optional.empty());
      })
      .filter(t -> asset.getEndDate().isAfter(t.getEndDate()))
      .or(() -> {
        throw new ServiceException(RESERVATION_ASSET_END_AFTER_ASSET_END_DATE, Optional.of(Map.of(ASSET_ID, asset.getId())));
      });
    return term;
  }

  @Override
  public Asset checkAvailability(final Term term, Integer quantity, Asset asset, String reservationId) {
    final List<ReservationAsset> ras = reservationAssetRepository.getOverLappingReservationAssets(term, asset.getId(), reservationId);

    List<LocalDate> dates = term.getStartDate().datesUntil(term.getEndDate().plusDays(1)).collect(Collectors.toList());
    dates.stream()
      .mapToLong(date -> getSumQuantityPerDay(ras, date))
      .filter(value -> value > asset.getQuantity() - quantity)
      .findFirst()
      .ifPresent(value -> {
        throw new ServiceException(RESERVATION_TO_ASSET_OVERFLOW, Optional.empty());
      });
    return asset;
  }

  public long getSumQuantityPerDay(final List<ReservationAsset> ras, LocalDate date) {
    return ras.stream()
      .filter(ra -> !ra.getStartDate().isAfter(date) && ra.getEndDate().isAfter(date))
      .mapToLong(ReservationAsset::getQuantity)
      .sum();
  }

}
