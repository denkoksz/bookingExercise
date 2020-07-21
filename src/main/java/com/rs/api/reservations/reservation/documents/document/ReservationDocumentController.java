package com.rs.api.reservations.reservation.documents.document;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rs.api.reservations.reservation.documents.ReservationDocumentResponse;
import com.rs.model.ReservationDocument;
import com.rs.service.ReservationDocumentService;
import com.rs.uris.Uris;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ReservationDocumentController {

  private final ReservationDocumentService reservationDocumentService;

  private final ReservationDocumentTranslator reservationDocumentTranslator;

  public ReservationDocumentController(ReservationDocumentService reservationDocumentService,
                                       ReservationDocumentTranslator reservationDocumentTranslator) {
    this.reservationDocumentService = reservationDocumentService;
    this.reservationDocumentTranslator = reservationDocumentTranslator;
  }

  @RequestMapping(path = Uris.PS.V1.RESERVATIONS.RESERVATION.DOCUMENTS.DOCUMENT.URI,
    method = GET,
    produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(OK)
  @Transactional
  public ReservationDocumentResponse getReservationDocument(final ReservationDocumentPathContext reservationDocumentPathContext) {
    final ReservationDocument reservationDocument =
      reservationDocumentService.getReservationDocument(reservationDocumentPathContext.getReservationId(),
        reservationDocumentPathContext.getDocumentId());
    return reservationDocumentTranslator.translate(reservationDocumentPathContext, reservationDocument);
  }
}
