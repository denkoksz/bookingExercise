package com.rs.api.reservations.reservation.documents;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.api.reservations.reservation.documents.document.ReservationDocumentPathContext;
import com.rs.api.reservations.reservation.documents.document.ReservationDocumentTranslator;
import com.rs.model.ReservationDocument;
import com.rs.service.ReservationDocumentService;
import com.rs.uris.Uris;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ReservationDocumentsController {

  private final ReservationDocumentService reservationDocumentService;

  private final ReservationDocumentTranslator reservationDocumentTranslator;

  private final ReservationDocumentsTranslator reservationDocumentsTranslator;

  public ReservationDocumentsController(final ReservationDocumentService reservationDocumentService,
                                        final ReservationDocumentTranslator reservationDocumentTranslator,
                                        final ReservationDocumentsTranslator reservationDocumentsTranslator) {
    this.reservationDocumentService = reservationDocumentService;
    this.reservationDocumentTranslator = reservationDocumentTranslator;
    this.reservationDocumentsTranslator = reservationDocumentsTranslator;
  }

  @RequestMapping(path = Uris.PS.V1.RESERVATIONS.RESERVATION.DOCUMENTS.URI,
    method = GET,
    produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(OK)
  @Transactional
  public ReservationDocumentsResponse getDocuments(final ReservationPathContext reservationPathContext) {
    final List<ReservationDocument> reservationDocuments =
      reservationDocumentService.getReservationDocuments(reservationPathContext.getReservationId());
    return reservationDocumentsTranslator.translate(reservationPathContext, reservationDocuments);
  }

  @RequestMapping(path = Uris.PS.V1.RESERVATIONS.RESERVATION.DOCUMENTS.URI,
    method = POST,
    consumes = APPLICATION_JSON_VALUE,
    produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(CREATED)
  @Transactional
  public ReservationDocumentResponse addDocument(final ReservationPathContext reservationPathContext,
                                                 @RequestBody final AddDocumentRequest addDocumentRequest) {
    final ReservationDocument reservationDocument =
      reservationDocumentService.addReservationDocument(reservationPathContext.getReservationId(), addDocumentRequest.getId());
    return reservationDocumentTranslator.translate(
      ReservationDocumentPathContext.of(reservationPathContext, reservationDocument.getExternalId()), reservationDocument);
  }
}