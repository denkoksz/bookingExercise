package com.rs.api.reservations;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rs.api.ApiPathContext;
import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.api.reservations.reservation.ReservationResponse;
import com.rs.api.reservations.reservation.ReservationTranslator;
import com.rs.model.Reservation;
import com.rs.repository.service.ReservationRepositoryService;
import com.rs.service.ReservationService;
import com.rs.uris.Uris.PS.V1.RESERVATIONS;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ReservationsController {

  private final ReservationService reservationService;

  private final ReservationsTranslator reservationsTranslator;

  private final ReservationTranslator reservationTranslator;

  private final ReservationRepositoryService reservationRepositoryService;

  public ReservationsController(final ReservationService reservationService,
                                final ReservationsTranslator reservationsTranslator,
                                final ReservationTranslator reservationTranslator,
                                ReservationRepositoryService reservationRepositoryService) {
    this.reservationService = reservationService;
    this.reservationsTranslator = reservationsTranslator;
    this.reservationTranslator = reservationTranslator;
    this.reservationRepositoryService = reservationRepositoryService;
  }

  @RequestMapping(path = RESERVATIONS.URI,
    method = POST,
    consumes = APPLICATION_JSON_VALUE,
    produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(CREATED)
  @Transactional
  public ReservationResponse create(final ApiPathContext apiPathContext,
                                    @RequestBody final AddReservationRequest addReservationRequest) {
    final Reservation reservation = reservationService.create(addReservationRequest.getTitle());
    return reservationTranslator.translate(
      ReservationPathContext.of(
        apiPathContext.getApp(),
        reservation.getId()),
      reservation);
  }
}
