package com.rs.api.reservations.reservation.documents.document;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.api.AbstractApiSpec;
import com.rs.client.ApiException;
import com.rs.client.ApiResponse;
import com.rs.client.model.Document;
import com.rs.client.model.ReservationServiceError;
import com.rs.message.ErrorMessages;
import com.rs.model.Reservation;
import com.rs.model.ReservationDocument;
import com.rs.service.ReservationDocumentService;
import com.rs.service.ReservationService;

import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ReservationDocumentControllerSpec extends AbstractApiSpec {

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ReservationDocumentService reservationDocumentService;

  private Reservation assignedReservation;
  private Reservation notAssignedReservation;
  private ReservationDocument reservationDocument;

  @Before
  public void setupExistingAsset() {
    assignedReservation = reservationService.create("first");
    notAssignedReservation = reservationService.create("second");
    reservationDocument = reservationDocumentService.addReservationDocument(assignedReservation.getId(),
      "ReservationDocumentControllerSpec");
  }

  @Test
  public void getReservationDocumentUnknownReservation() {
    final ReservationServiceError ex = assertApiException(NOT_FOUND, () -> getDocument("UNKNOWN", reservationDocument.getExternalId()));

    MatcherAssert.assertThat(ex.getErrorCode(), Matchers.is(ErrorMessages.RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void getReservationDocumentWithoutDocument() {
    final ReservationServiceError ex = assertApiException(NOT_FOUND, () -> getDocument(assignedReservation.getId(), "UNKNOWN"));

    MatcherAssert.assertThat(ex.getErrorCode(), Matchers.is(ErrorMessages.DOCUMENT_NOT_FOUND.getCode()));
  }

  @Test
  public void getReservationDocumentWithoutAssignment() {
    final ReservationServiceError ex = assertApiException(NOT_FOUND, () ->
      getDocument(notAssignedReservation.getId(), reservationDocument.getExternalId()));

    MatcherAssert.assertThat(ex.getErrorCode(), Matchers.is(ErrorMessages.RESERVATION_DOCUMENT_NOT_ASSIGNED.getCode()));
  }

  @Test
  public void getReservationDocumentWithDocument() throws ApiException {
    final ApiResponse<Document> document = getDocument(assignedReservation.getId(), reservationDocument.getExternalId());

    assertThat(document.getStatusCode(), is(SC_OK));
    MatcherAssert.assertThat(document.getData().getId(), is(reservationDocument.getExternalId()));
  }
}

