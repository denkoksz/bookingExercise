package com.rs.api.reservations.reservation;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rs.model.Reservation;
import com.rs.service.ReservationService;
import com.rs.uris.Uris;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ReservationController {

  private final ReservationService reservationService;

  private final ReservationTranslator reservationTranslator;

  public ReservationController(final ReservationService reservationService, final ReservationTranslator reservationTranslator) {
    this.reservationService = reservationService;
    this.reservationTranslator = reservationTranslator;
  }

  @RequestMapping(path = Uris.PS.V1.RESERVATIONS.RESERVATION.URI,
    method = GET,
    produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(OK)
  @Transactional
  public ReservationResponse get(final ReservationPathContext pathContext) {
    final Reservation reservation = reservationService.getReservationById(pathContext.getReservationId());
    return reservationTranslator.translate(pathContext, reservation);
  }
}
