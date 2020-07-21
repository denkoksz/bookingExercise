package com.rs.repository.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rs.api.exception.ServiceException;
import com.rs.message.ErrorMessageConstants;
import com.rs.model.Reservation;
import com.rs.repository.ReservationRepository;
import com.rs.repository.service.AssetRepositoryService;
import com.rs.repository.service.ReservationRepositoryService;
import com.rs.service.IdGeneratorService;

import static com.rs.api.ContextConstants.VAR_RESERVATION_ID;
import static com.rs.message.ErrorMessages.ASSET_NOT_FOUND;
import static com.rs.message.ErrorMessages.RESERVATION_NOT_FOUND;
import static com.rs.model.EntityConsts.PREFIX_RESERVATION;

@Service
public class ReservationRepositoryServiceImpl implements ReservationRepositoryService {

  private final ReservationRepository reservationRepository;

  private final IdGeneratorService idGeneratorService;

  private final AssetRepositoryService assetRepositoryService;

  public ReservationRepositoryServiceImpl(ReservationRepository reservationRepository, IdGeneratorService idGeneratorService,
                                          AssetRepositoryService assetRepositoryService) {
    this.reservationRepository = reservationRepository;
    this.idGeneratorService = idGeneratorService;
    this.assetRepositoryService = assetRepositoryService;
  }

  @Override
  public Reservation create(String title) {
    return saveReservation(new Reservation()
      .setId(idGeneratorService.getId(PREFIX_RESERVATION)))
      .setTitle(title);
  }

  @Override
  public Reservation getById(final String id) {
    return reservationRepository.findById(id)
      .orElseThrow(() -> new ServiceException(RESERVATION_NOT_FOUND, Optional.of(Map.of(VAR_RESERVATION_ID, id))));
  }

  @Override
  public List<Reservation> getReservations(final String assetId) {
    assetRepositoryService.findById(assetId)
      .orElseThrow(() -> new ServiceException(ASSET_NOT_FOUND, Optional.of(Map.of(ErrorMessageConstants.ASSET_ID, assetId))));
    return reservationRepository.getReservations(assetId);
  }

  private Reservation saveReservation(final Reservation reservation) {
    return reservationRepository.saveAndFlush(reservation);
  }
}