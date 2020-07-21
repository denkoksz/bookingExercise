package com.rs.api;

import java.util.concurrent.Callable;

import org.json.JSONObject;
import org.junit.Before;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.rs.AbstractSpec;
import com.rs.client.ApiClient;
import com.rs.client.ApiException;
import com.rs.client.ApiResponse;
import com.rs.client.Configuration;
import com.rs.client.api.AssetsApi;
import com.rs.client.api.ConfigurationApi;
import com.rs.client.api.ContactsApi;
import com.rs.client.api.DefaultApi;
import com.rs.client.api.DocumentsApi;
import com.rs.client.api.ReservationsApi;
import com.rs.client.model.AddAssetBody;
import com.rs.client.model.AddContactBody;
import com.rs.client.model.AddDocumentBody;
import com.rs.client.model.AssetReservations;
import com.rs.client.model.Contacts;
import com.rs.client.model.CreateReservationBody;
import com.rs.client.model.Document;
import com.rs.client.model.Documents;
import com.rs.client.model.Field;
import com.rs.client.model.Fieldset;
import com.rs.client.model.FreeTermQuantities;
import com.rs.client.model.Reservation;
import com.rs.client.model.ReservationAsset;
import com.rs.client.model.ReservationAssets;
import com.rs.client.model.ReservationServiceError;
import com.rs.client.model.Reservations;
import com.rs.client.model.Root;
import junit.framework.AssertionFailedError;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public abstract class AbstractApiSpec extends AbstractSpec {

  protected static final String APP = "basems";

  private final ApiClient apiClient = Configuration.getDefaultApiClient();
  private DefaultApi defaultApi;
  private ReservationsApi reservationsApi;
  private AssetsApi assetsApi;
  private DocumentsApi documentsApi;
  private ConfigurationApi configurationApi;
  private ContactsApi contactsApi;

  @LocalServerPort
  private int localServerPort;

  @Before
  public void setApiPath() {
    apiClient.setBasePath("http://localhost:" + localServerPort);
  }

  Root rootGet() throws ApiException {
    return ensureDefaultApi().rootGet();
  }

  protected ApiResponse<Reservation> createReservation(final CreateReservationBody createReservationBody) throws ApiException {
    return ensureReservationsApi().apiV1AppReservationsPostWithHttpInfo(APP, createReservationBody);
  }

  protected ApiResponse<Reservations> getReservations(final Integer limit, final Integer offset) throws ApiException {
    return ensureReservationsApi().apiV1AppReservationsGetWithHttpInfo(APP, limit, offset);
  }

  protected ApiResponse<AssetReservations> getReservationsOfAnAsset(final String assetId,
                                                                    final Integer offset,
                                                                    final Integer limit) throws ApiException {
    return ensureAssetsApi().apiV1AppAssetsAssetIdReservationsGetWithHttpInfo(APP, assetId, offset, limit);
  }

  protected ApiResponse<Reservation> getReservation(final String reservationId, final String embed) throws ApiException {
    return ensureReservationsApi().apiV1AppReservationsReservationIdGetWithHttpInfo(APP, reservationId, embed);
  }

  protected ApiResponse<ReservationAssets> getReservationAssets(final String reservationId,
                                                                final Integer limit,
                                                                final Integer offset) throws ApiException {
    return ensureReservationsApi().apiV1AppReservationsReservationIdAssetsGetWithHttpInfo(APP, reservationId, limit, offset);
  }

  protected ApiResponse<Void> addAsset(final String reservationId, final AddAssetBody addAssetBody) throws ApiException {
    return ensureReservationsApi().apiV1AppReservationsReservationIdAssetsPostWithHttpInfo(APP, reservationId, addAssetBody);
  }

  protected ApiResponse<ReservationAsset> getReservationAsset(final String reservationId, final String assetId) throws ApiException {
    return ensureReservationsApi().apiV1AppReservationsReservationIdAssetsAssetIdGetWithHttpInfo(APP, reservationId, assetId);
  }

  protected ApiResponse<Void> deleteReservationAsset(final String reservationId, final String assetId) throws ApiException {
    return ensureReservationsApi().apiV1AppReservationsReservationIdAssetsAssetIdDeleteWithHttpInfo(APP, reservationId, assetId);
  }

  protected ApiResponse<Void> deleteReservationContact(final String reservationId, final String contactId) throws ApiException {
    return ensureContactsApi()
      .apiV1AppReservationsReservationIdContactsContactIdDeleteWithHttpInfo(APP, reservationId, contactId);
  }

  protected ApiResponse<Document> addDocument(final String reservationId,
                                              final AddDocumentBody addDocumentBody) throws ApiException {
    return ensureDocumentsApi().apiV1AppReservationsReservationIdDocumentsPostWithHttpInfo(APP, reservationId, addDocumentBody);
  }

  protected ApiResponse<Documents> getDocuments(final String reservationId) throws ApiException {
    return ensureDocumentsApi().apiV1AppReservationsReservationIdDocumentsGetWithHttpInfo(APP, reservationId);
  }

  protected ApiResponse<Document> getDocument(final String reservationId, final String documentId) throws ApiException {
    return ensureDocumentsApi()
      .apiV1AppReservationsReservationIdDocumentsDocumentIdGetWithHttpInfo(APP, reservationId, documentId);
  }

  protected Fieldset getFieldSetConfig(final String fieldSetId) throws ApiException {
    return ensureConfigurationsApi().apiV1AppConfigFieldSetIdFieldsGet(APP, fieldSetId);
  }

  protected Field getFieldConfig(final String fieldSetId, final String fieldId) throws ApiException {
    return ensureConfigurationsApi().apiV1AppConfigFieldSetIdFieldsFieldIdGet(APP, fieldSetId, fieldId);
  }

  protected ApiResponse<Contacts> getReservationContacts(final String reservationId) throws ApiException {
    return ensureContactsApi().apiV1AppReservationsReservationIdContactsGetWithHttpInfo(APP, reservationId);
  }

  protected ApiResponse<Void> addReservationContacts(final String reservationId, final AddContactBody body) throws ApiException {
    return ensureContactsApi().apiV1AppReservationsReservationIdContactsPostWithHttpInfo(APP, reservationId, body);
  }

  protected ApiResponse<FreeTermQuantities> getFreeTermQuantities(final String startDate, final String endDate, final String assetId) throws ApiException {
    return ensureAssetsApi().apiV1AppAssetsAssetIdFreetermquantitiesGetWithHttpInfo(APP, assetId, startDate, endDate);
  }

  protected ReservationServiceError assertApiException(final HttpStatus httpStatus, final Callable callable) {
    try {
      callable.call();
    } catch (final Throwable throwable) {
      if (throwable instanceof ApiException) {
        final ApiException apiException = (ApiException) throwable;
        assertThat(apiException.getCode(), is(httpStatus.value()));
        return createServiceError(new JSONObject(apiException.getResponseBody()));
      }
      throw createAssertionFailedError(throwable);
    }
    throw createAssertionFailedError();
  }

  private ReservationServiceError createServiceError(final JSONObject json) {
    return new ReservationServiceError()
      .errorCode(json.get("errorCode").toString())
      .errorId(json.get("errorId").toString())
      .errorMessage(json.get("errorMessage").toString());
  }

  private AssertionFailedError createAssertionFailedError(final Throwable throwable) {
    final String message = "Expected ApiException to be thrown, but " + throwable.getMessage() + " was thrown.";
    return new AssertionFailedError(message);
  }

  private AssertionFailedError createAssertionFailedError() {
    final String message = "Expected ApiException to be thrown, but nothing was thrown.";
    return new AssertionFailedError(message);
  }

  private AssetsApi ensureAssetsApi() {
    if (assetsApi == null) {
      assetsApi = new AssetsApi(apiClient);
    }
    return assetsApi;
  }

  private DefaultApi ensureDefaultApi() {
    if (defaultApi == null) {
      defaultApi = new DefaultApi(apiClient);
    }
    return defaultApi;
  }

  private ReservationsApi ensureReservationsApi() {
    if (reservationsApi == null) {
      reservationsApi = new ReservationsApi(apiClient);
    }
    return reservationsApi;
  }

  private ContactsApi ensureContactsApi() {
    if (contactsApi == null) {
      contactsApi = new ContactsApi(apiClient);
    }
    return contactsApi;
  }

  private ConfigurationApi ensureConfigurationsApi() {
    if (configurationApi == null) {
      configurationApi = new ConfigurationApi(apiClient);
    }
    return configurationApi;
  }

  private DocumentsApi ensureDocumentsApi() {
    if (documentsApi == null) {
      documentsApi = new DocumentsApi(apiClient);
    }
    return documentsApi;
  }
}