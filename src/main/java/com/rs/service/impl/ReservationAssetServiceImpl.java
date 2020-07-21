package com.rs.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rs.model.Asset;
import com.rs.model.Reservation;
import com.rs.model.ReservationAsset;
import com.rs.model.Term;
import com.rs.repository.ReservationAssetRepository;
import com.rs.repository.service.AssetRepositoryService;
import com.rs.repository.service.ReservationAssetRepositoryService;
import com.rs.repository.service.ReservationRepositoryService;
import com.rs.service.ReservationAssetCheckService;
import com.rs.service.ReservationAssetService;

@Service
public class ReservationAssetServiceImpl implements ReservationAssetService {

  private final ReservationRepositoryService reservationRepositoryService;

  private final AssetRepositoryService assetRepositoryService;

  private final ReservationAssetRepositoryService reservationAssetRepositoryService;

  private final ReservationAssetCheckService reservationAssetCheckService;

  private final ReservationAssetRepository reservationAssetRepository;

  public ReservationAssetServiceImpl(final ReservationRepositoryService reservationRepositoryService,
                                     final AssetRepositoryService assetRepositoryService,
                                     final ReservationAssetRepositoryService reservationAssetRepositoryService,
                                     final ReservationAssetCheckService reservationAssetCheckService,
                                     final ReservationAssetRepository reservationAssetRepository) {
    this.reservationRepositoryService = reservationRepositoryService;
    this.assetRepositoryService = assetRepositoryService;
    this.reservationAssetRepositoryService = reservationAssetRepositoryService;
    this.reservationAssetCheckService = reservationAssetCheckService;
    this.reservationAssetRepository = reservationAssetRepository;
  }


  @Override
  public List<Asset> getAssets(final String reservationId) {
    reservationRepositoryService.getById(reservationId);
    return assetRepositoryService.getAssets(reservationId);
  }

  @Override
  public Asset getAsset(final String reservationId, final String assetId) {
    final Reservation reservation = reservationRepositoryService.getById(reservationId);
    final Asset asset = assetRepositoryService.getById(assetId);
    reservationAssetRepositoryService.getByReservationAsset(reservation, asset);
    return asset;
  }

  @Override
  public Asset addAsset(final String reservationId, final String assetId, Integer quantity, Term term) {
    final Reservation reservation = reservationRepositoryService.getById(reservationId);
    final Asset asset = assetRepositoryService.getById(assetId);

    reservationAssetCheckService.checkQuantity(asset, quantity);
    reservationAssetCheckService.checkTerm(asset, term);
    reservationAssetCheckService.checkAvailability(term, quantity, asset, reservationId);

    Optional<ReservationAsset> reservationAsset = reservationAssetRepositoryService.findByReservationAndAsset(reservation, asset);

    reservationAsset
      .or(() -> Optional.of(reservationAssetRepositoryService.create(reservation, asset, quantity, term)))
      .map(ra -> reservationAssetRepositoryService.update(ra, quantity, term));
    return asset;
  }

  @Override
  public void deleteReservationAsset(final String reservationId, final String assetId) {
    final Reservation reservation = reservationRepositoryService.getById(reservationId);
    final Asset asset = assetRepositoryService.getById(assetId);
    final ReservationAsset reservationAsset = reservationAssetRepositoryService.getByReservationAsset(reservation, asset);
    reservationAssetRepositoryService.delete(reservationAsset);
  }

  @Override
  public List<ReservationAsset> getOverLappingReservationAssets(final Term term, final String assetId, final String reservationId) {
    return reservationAssetRepository.getOverLappingReservationAssets(term, assetId, reservationId);
  }
}

