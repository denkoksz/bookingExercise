package com.rs.repository.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rs.api.ContextConstants;
import com.rs.api.exception.ServiceException;
import com.rs.message.ErrorMessages;
import com.rs.model.EntityConsts;
import com.rs.model.ReservationDocument;
import com.rs.repository.ReservationDocumentRepository;
import com.rs.repository.service.ReservationDocumentRepositoryService;
import com.rs.service.IdGeneratorService;

@Service
public class ReservationDocumentRepositoryServiceImpl implements ReservationDocumentRepositoryService {

  private final ReservationDocumentRepository reservationDocumentRepository;

  private final IdGeneratorService idGeneratorService;

  public ReservationDocumentRepositoryServiceImpl(IdGeneratorService idGeneratorService,
                                                  ReservationDocumentRepository reservationDocumentRepository) {
    this.idGeneratorService = idGeneratorService;
    this.reservationDocumentRepository = reservationDocumentRepository;
  }

  @Override
  public Optional<ReservationDocument> findByExternalId(final String externalId) {
    return reservationDocumentRepository.findByExternalId(externalId);
  }

  @Override
  public ReservationDocument getByExternalId(final String externalId) {
    return reservationDocumentRepository.findByExternalId(externalId)
      .orElseThrow(() -> new ServiceException(ErrorMessages.DOCUMENT_NOT_FOUND, Optional.of(Map.of(ContextConstants.VAR_DOCUMENT_ID,
        externalId))));
  }

  @Override
  public ReservationDocument create(final String externalId) {
    return saveReservationDocument(new ReservationDocument()
      .setId(idGeneratorService.getId(EntityConsts.PREFIX_RESERVATION_DOCUMENT))
      .setExternalId(externalId));
  }

  @Override
  public List<ReservationDocument> findAllByReservationId(final String reservationId) {
    return reservationDocumentRepository.findAllByReservationId(reservationId);
  }

  private ReservationDocument saveReservationDocument(final ReservationDocument reservationDocument) {
    return reservationDocumentRepository.saveAndFlush(reservationDocument);
  }
}
