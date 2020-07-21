/*
 * RESERVATION SERVICE REST API
 * API for the Reservation Service. The API covers the functionality to create, read and update reservations and their metadata stored in the DAM metadata store, add and remove assets from the reservations.  The API is based on REST and only consumes JSON requests.  **Security**  This API is protected via the standard OIDC authentication provided by MIT. In order to use the API, an access token must be retrieved first and then used in the Authorization header with \"Bearer [Access Token]\". 
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.rs.client.api;

import com.rs.client.ApiCallback;
import com.rs.client.ApiClient;
import com.rs.client.ApiException;
import com.rs.client.ApiResponse;
import com.rs.client.Configuration;
import com.rs.client.Pair;
import com.rs.client.ProgressRequestBody;
import com.rs.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import com.rs.client.model.AddDocumentBody;
import com.rs.client.model.Document;
import com.rs.client.model.Documents;
import com.rs.client.model.ReservationServiceError;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentsApi {
    private ApiClient apiClient;

    public DocumentsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public DocumentsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for apiV1AppReservationsReservationIdDocumentsDocumentIdDelete
     * @param app The application (required)
     * @param reservationId the reservation id (required)
     * @param documentId the id of a document (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call apiV1AppReservationsReservationIdDocumentsDocumentIdDeleteCall(String app, String reservationId, String documentId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/api/v1/{app}/reservations/{reservationId}/documents/{documentId}"
            .replaceAll("\\{" + "app" + "\\}", apiClient.escapeString(app.toString()))
            .replaceAll("\\{" + "reservationId" + "\\}", apiClient.escapeString(reservationId.toString()))
            .replaceAll("\\{" + "documentId" + "\\}", apiClient.escapeString(documentId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "OAuth2ClientCredentialsSecurity" };
        return apiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call apiV1AppReservationsReservationIdDocumentsDocumentIdDeleteValidateBeforeCall(String app, String reservationId, String documentId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'app' is set
        if (app == null) {
            throw new ApiException("Missing the required parameter 'app' when calling apiV1AppReservationsReservationIdDocumentsDocumentIdDelete(Async)");
        }
        
        // verify the required parameter 'reservationId' is set
        if (reservationId == null) {
            throw new ApiException("Missing the required parameter 'reservationId' when calling apiV1AppReservationsReservationIdDocumentsDocumentIdDelete(Async)");
        }
        
        // verify the required parameter 'documentId' is set
        if (documentId == null) {
            throw new ApiException("Missing the required parameter 'documentId' when calling apiV1AppReservationsReservationIdDocumentsDocumentIdDelete(Async)");
        }
        
        
        com.squareup.okhttp.Call call = apiV1AppReservationsReservationIdDocumentsDocumentIdDeleteCall(app, reservationId, documentId, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * Remove the specific document from a reservation specified by ReservationId
     * 
     * @param app The application (required)
     * @param reservationId the reservation id (required)
     * @param documentId the id of a document (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public void apiV1AppReservationsReservationIdDocumentsDocumentIdDelete(String app, String reservationId, String documentId) throws ApiException {
        apiV1AppReservationsReservationIdDocumentsDocumentIdDeleteWithHttpInfo(app, reservationId, documentId);
    }

    /**
     * Remove the specific document from a reservation specified by ReservationId
     * 
     * @param app The application (required)
     * @param reservationId the reservation id (required)
     * @param documentId the id of a document (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Void> apiV1AppReservationsReservationIdDocumentsDocumentIdDeleteWithHttpInfo(String app, String reservationId, String documentId) throws ApiException {
        com.squareup.okhttp.Call call = apiV1AppReservationsReservationIdDocumentsDocumentIdDeleteValidateBeforeCall(app, reservationId, documentId, null, null);
        return apiClient.execute(call);
    }

    /**
     * Remove the specific document from a reservation specified by ReservationId (asynchronously)
     * 
     * @param app The application (required)
     * @param reservationId the reservation id (required)
     * @param documentId the id of a document (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call apiV1AppReservationsReservationIdDocumentsDocumentIdDeleteAsync(String app, String reservationId, String documentId, final ApiCallback<Void> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = apiV1AppReservationsReservationIdDocumentsDocumentIdDeleteValidateBeforeCall(app, reservationId, documentId, progressListener, progressRequestListener);
        apiClient.executeAsync(call, callback);
        return call;
    }
    /**
     * Build call for apiV1AppReservationsReservationIdDocumentsDocumentIdGet
     * @param app The application (required)
     * @param reservationId the reservation id (required)
     * @param documentId the id of a document (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call apiV1AppReservationsReservationIdDocumentsDocumentIdGetCall(String app, String reservationId, String documentId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/api/v1/{app}/reservations/{reservationId}/documents/{documentId}"
            .replaceAll("\\{" + "app" + "\\}", apiClient.escapeString(app.toString()))
            .replaceAll("\\{" + "reservationId" + "\\}", apiClient.escapeString(reservationId.toString()))
            .replaceAll("\\{" + "documentId" + "\\}", apiClient.escapeString(documentId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "OAuth2ClientCredentialsSecurity" };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call apiV1AppReservationsReservationIdDocumentsDocumentIdGetValidateBeforeCall(String app, String reservationId, String documentId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'app' is set
        if (app == null) {
            throw new ApiException("Missing the required parameter 'app' when calling apiV1AppReservationsReservationIdDocumentsDocumentIdGet(Async)");
        }
        
        // verify the required parameter 'reservationId' is set
        if (reservationId == null) {
            throw new ApiException("Missing the required parameter 'reservationId' when calling apiV1AppReservationsReservationIdDocumentsDocumentIdGet(Async)");
        }
        
        // verify the required parameter 'documentId' is set
        if (documentId == null) {
            throw new ApiException("Missing the required parameter 'documentId' when calling apiV1AppReservationsReservationIdDocumentsDocumentIdGet(Async)");
        }
        
        
        com.squareup.okhttp.Call call = apiV1AppReservationsReservationIdDocumentsDocumentIdGetCall(app, reservationId, documentId, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * Get the list of documents of a reservation specified by reservation id
     * 
     * @param app The application (required)
     * @param reservationId the reservation id (required)
     * @param documentId the id of a document (required)
     * @return Document
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Document apiV1AppReservationsReservationIdDocumentsDocumentIdGet(String app, String reservationId, String documentId) throws ApiException {
        ApiResponse<Document> resp = apiV1AppReservationsReservationIdDocumentsDocumentIdGetWithHttpInfo(app, reservationId, documentId);
        return resp.getData();
    }

    /**
     * Get the list of documents of a reservation specified by reservation id
     * 
     * @param app The application (required)
     * @param reservationId the reservation id (required)
     * @param documentId the id of a document (required)
     * @return ApiResponse&lt;Document&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Document> apiV1AppReservationsReservationIdDocumentsDocumentIdGetWithHttpInfo(String app, String reservationId, String documentId) throws ApiException {
        com.squareup.okhttp.Call call = apiV1AppReservationsReservationIdDocumentsDocumentIdGetValidateBeforeCall(app, reservationId, documentId, null, null);
        Type localVarReturnType = new TypeToken<Document>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get the list of documents of a reservation specified by reservation id (asynchronously)
     * 
     * @param app The application (required)
     * @param reservationId the reservation id (required)
     * @param documentId the id of a document (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call apiV1AppReservationsReservationIdDocumentsDocumentIdGetAsync(String app, String reservationId, String documentId, final ApiCallback<Document> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = apiV1AppReservationsReservationIdDocumentsDocumentIdGetValidateBeforeCall(app, reservationId, documentId, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<Document>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for apiV1AppReservationsReservationIdDocumentsGet
     * @param app The application (required)
     * @param reservationId the reservation id (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call apiV1AppReservationsReservationIdDocumentsGetCall(String app, String reservationId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/api/v1/{app}/reservations/{reservationId}/documents"
            .replaceAll("\\{" + "app" + "\\}", apiClient.escapeString(app.toString()))
            .replaceAll("\\{" + "reservationId" + "\\}", apiClient.escapeString(reservationId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "OAuth2ClientCredentialsSecurity" };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call apiV1AppReservationsReservationIdDocumentsGetValidateBeforeCall(String app, String reservationId, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'app' is set
        if (app == null) {
            throw new ApiException("Missing the required parameter 'app' when calling apiV1AppReservationsReservationIdDocumentsGet(Async)");
        }
        
        // verify the required parameter 'reservationId' is set
        if (reservationId == null) {
            throw new ApiException("Missing the required parameter 'reservationId' when calling apiV1AppReservationsReservationIdDocumentsGet(Async)");
        }
        
        
        com.squareup.okhttp.Call call = apiV1AppReservationsReservationIdDocumentsGetCall(app, reservationId, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * Get the list of documents of a reservation specified by reservation id
     * 
     * @param app The application (required)
     * @param reservationId the reservation id (required)
     * @return Documents
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Documents apiV1AppReservationsReservationIdDocumentsGet(String app, String reservationId) throws ApiException {
        ApiResponse<Documents> resp = apiV1AppReservationsReservationIdDocumentsGetWithHttpInfo(app, reservationId);
        return resp.getData();
    }

    /**
     * Get the list of documents of a reservation specified by reservation id
     * 
     * @param app The application (required)
     * @param reservationId the reservation id (required)
     * @return ApiResponse&lt;Documents&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Documents> apiV1AppReservationsReservationIdDocumentsGetWithHttpInfo(String app, String reservationId) throws ApiException {
        com.squareup.okhttp.Call call = apiV1AppReservationsReservationIdDocumentsGetValidateBeforeCall(app, reservationId, null, null);
        Type localVarReturnType = new TypeToken<Documents>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get the list of documents of a reservation specified by reservation id (asynchronously)
     * 
     * @param app The application (required)
     * @param reservationId the reservation id (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call apiV1AppReservationsReservationIdDocumentsGetAsync(String app, String reservationId, final ApiCallback<Documents> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = apiV1AppReservationsReservationIdDocumentsGetValidateBeforeCall(app, reservationId, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<Documents>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for apiV1AppReservationsReservationIdDocumentsPost
     * @param app The application (required)
     * @param reservationId the reservation id (required)
     * @param body  (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call apiV1AppReservationsReservationIdDocumentsPostCall(String app, String reservationId, AddDocumentBody body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;
        
        // create path and map variables
        String localVarPath = "/api/v1/{app}/reservations/{reservationId}/documents"
            .replaceAll("\\{" + "app" + "\\}", apiClient.escapeString(app.toString()))
            .replaceAll("\\{" + "reservationId" + "\\}", apiClient.escapeString(reservationId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "OAuth2ClientCredentialsSecurity" };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call apiV1AppReservationsReservationIdDocumentsPostValidateBeforeCall(String app, String reservationId, AddDocumentBody body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'app' is set
        if (app == null) {
            throw new ApiException("Missing the required parameter 'app' when calling apiV1AppReservationsReservationIdDocumentsPost(Async)");
        }
        
        // verify the required parameter 'reservationId' is set
        if (reservationId == null) {
            throw new ApiException("Missing the required parameter 'reservationId' when calling apiV1AppReservationsReservationIdDocumentsPost(Async)");
        }
        
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling apiV1AppReservationsReservationIdDocumentsPost(Async)");
        }
        
        
        com.squareup.okhttp.Call call = apiV1AppReservationsReservationIdDocumentsPostCall(app, reservationId, body, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * Add a document to a reservation specified by reservation id
     * 
     * @param app The application (required)
     * @param reservationId the reservation id (required)
     * @param body  (required)
     * @return Document
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Document apiV1AppReservationsReservationIdDocumentsPost(String app, String reservationId, AddDocumentBody body) throws ApiException {
        ApiResponse<Document> resp = apiV1AppReservationsReservationIdDocumentsPostWithHttpInfo(app, reservationId, body);
        return resp.getData();
    }

    /**
     * Add a document to a reservation specified by reservation id
     * 
     * @param app The application (required)
     * @param reservationId the reservation id (required)
     * @param body  (required)
     * @return ApiResponse&lt;Document&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Document> apiV1AppReservationsReservationIdDocumentsPostWithHttpInfo(String app, String reservationId, AddDocumentBody body) throws ApiException {
        com.squareup.okhttp.Call call = apiV1AppReservationsReservationIdDocumentsPostValidateBeforeCall(app, reservationId, body, null, null);
        Type localVarReturnType = new TypeToken<Document>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Add a document to a reservation specified by reservation id (asynchronously)
     * 
     * @param app The application (required)
     * @param reservationId the reservation id (required)
     * @param body  (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call apiV1AppReservationsReservationIdDocumentsPostAsync(String app, String reservationId, AddDocumentBody body, final ApiCallback<Document> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = apiV1AppReservationsReservationIdDocumentsPostValidateBeforeCall(app, reservationId, body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<Document>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
