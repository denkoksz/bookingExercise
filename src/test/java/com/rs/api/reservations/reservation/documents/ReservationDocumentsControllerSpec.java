package com.rs.api.reservations.reservation.documents;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.api.AbstractApiSpec;
import com.rs.client.ApiException;
import com.rs.client.ApiResponse;
import com.rs.client.model.AddDocumentBody;
import com.rs.client.model.Document;
import com.rs.client.model.Documents;
import com.rs.client.model.ReservationServiceError;
import com.rs.model.Reservation;
import com.rs.service.ReservationService;
import com.rs.service.nosql.NoSQLService;

import static com.rs.message.ErrorMessages.RESERVATION_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ReservationDocumentsControllerSpec extends AbstractApiSpec {

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private NoSQLService noSQLService;

  private Reservation reservation;

  @Before
  public void setupExistingAsset() {
    reservation = reservationService.create("first");
  }

  @Test
  public void addDocumentToUnknownReservation() {
    final AddDocumentBody addDocumentBody = new AddDocumentBody();
    addDocumentBody.setId("documentId");

    final ReservationServiceError ex = assertApiException(NOT_FOUND, () -> addDocument("UNKNOWN", addDocumentBody));

    assertThat(ex.getErrorCode(), is(RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void addDocumentToKnownReservation() throws ApiException {
    final AddDocumentBody addDocumentBody = new AddDocumentBody();
    addDocumentBody.setId("documentId");

    final ApiResponse<Document> resp = addDocument(reservation.getId(), addDocumentBody);

    assertThat(resp.getStatusCode(), is(SC_CREATED));
    assertThat(resp.getData().getId(), is("documentId"));
  }

  @Test
  public void addDocumentTwiceToKnownReservation() throws ApiException {
    final AddDocumentBody addDocumentBody = new AddDocumentBody();
    addDocumentBody.setId("documentId");
    addDocument(reservation.getId(), addDocumentBody);

    final ApiResponse<Document> resp = addDocument(reservation.getId(), addDocumentBody);

    assertThat(resp.getStatusCode(), is(SC_CREATED));
    assertThat(resp.getData().getId(), is("documentId"));
  }

  @Test
  public void addDocumentsToKnownReservation() throws ApiException {
    final AddDocumentBody addDocumentBody = new AddDocumentBody();
    addDocumentBody.setId("documentId1");
    final ApiResponse<Document> resp1 = addDocument(reservation.getId(), addDocumentBody);
    assertThat(resp1.getStatusCode(), is(SC_CREATED));

    addDocumentBody.setId("documentId2");
    final ApiResponse<Document> resp2 = addDocument(reservation.getId(), addDocumentBody);

    assertThat(resp2.getStatusCode(), is(SC_CREATED));
    assertThat(resp2.getData().getId(), is("documentId2"));
  }

  @Test
  public void getDocumentsUnknownReservation() {
    final ReservationServiceError ex = assertApiException(NOT_FOUND, () -> getDocuments("UNKNOWN"));

    assertThat(ex.getErrorCode(), is(RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void getDocumentsWithoutDocuments() throws ApiException {
    final ApiResponse<Documents> documents = getDocuments(reservation.getId());

    assertThat(documents.getStatusCode(), is(SC_OK));
    assertThat(documents.getData().getItems().size(), is(0));
  }

  @Test
  public void getDocumentsWithDocuments() throws ApiException {
    final AddDocumentBody addDocumentBody = new AddDocumentBody();
    addDocumentBody.setId("documentId1");
    final ApiResponse<Document> resp1 = addDocument(reservation.getId(), addDocumentBody);
    assertThat(resp1.getStatusCode(), is(SC_CREATED));

    addDocumentBody.setId("documentId2");
    final ApiResponse<Document> resp2 = addDocument(reservation.getId(), addDocumentBody);
    assertThat(resp2.getStatusCode(), is(SC_CREATED));

    final ApiResponse<Documents> documents = getDocuments(reservation.getId());

    assertThat(documents.getStatusCode(), is(SC_OK));
    assertThat(documents.getData().getItems().size(), is(2));
  }

  @After
  public void clear() {
    noSQLService.clearIndex();
  }
}
