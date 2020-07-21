package com.rs.repository.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rs.api.exception.ServiceException;
import com.rs.message.ErrorMessageConstants;
import com.rs.message.ErrorMessages;
import com.rs.model.Asset;
import com.rs.model.Reservation;
import com.rs.model.ReservationAsset;
import com.rs.model.Term;
import com.rs.repository.ReservationAssetRepository;
import com.rs.repository.service.ReservationAssetRepositoryService;

@Service
public class ReservationAssetRepositoryServiceImpl implements ReservationAssetRepositoryService {

  private final ReservationAssetRepository reservationAssetRepository;

  public ReservationAssetRepositoryServiceImpl(ReservationAssetRepository reservationAssetRepository) {
    this.reservationAssetRepository = reservationAssetRepository;
  }

  @Override
  public ReservationAsset create(final Reservation reservation,
                                 final Asset asset,
                                 final Integer quantity,
                                 final Term term) {
    return saveReservationAsset(new ReservationAsset()
      .setReservationId(reservation.getId())
      .setAssetId(asset.getId())
      .setQuantity(quantity)
      .setTerm(term));
  }

  @Override
  public Optional<ReservationAsset> findByReservationAndAsset(final Reservation reservation, final Asset asset) {
    return reservationAssetRepository.findByReservationIdAndAssetId(reservation.getId(), asset.getId());
  }


  @Override
  public ReservationAsset getByReservationAsset(final Reservation reservation, final Asset asset) {
    return findByReservationAndAsset(reservation, asset)
      .orElseThrow(() -> new ServiceException(ErrorMessages.RESERVATION_ASSET_NOT_FOUND,
        Optional.of(Map.of(ErrorMessageConstants.ASSET_ID, asset.getId(), ErrorMessageConstants.RESERVATION_ID, reservation.getId()))));
  }

  @Override
  public ReservationAsset update(final ReservationAsset reservationAsset,
                                 final Integer quantity,
                                 final Term term) {
    if( !quantity.equals(reservationAsset.getQuantity())) {
      reservationAsset.setQuantity(quantity);
    }
    if (!term.getStartDate().equals(reservationAsset.getStartDate())) {
      reservationAsset.setStartDate(term.getStartDate());
    }
    if (!term.getEndDate().equals(reservationAsset.getEndDate())){
      reservationAsset.setEndDate(term.getEndDate());
    }
    return saveReservationAsset(reservationAsset);
  }

  @Override
  public void delete(final ReservationAsset reservationAsset) {
    reservationAssetRepository.delete(reservationAsset);
  }

  private ReservationAsset saveReservationAsset(final ReservationAsset reservationAsset) {
    return reservationAssetRepository.saveAndFlush(reservationAsset);
  }
}