package com.rs.message;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ErrorMessages {

  public static final ServiceMessage UNKNOWN =
    new ServiceMessage("E0001", INTERNAL_SERVER_ERROR, "Internal Server Error, check back later or contact support");
  public static final ServiceMessage STATE_CHANGED_REFRESH_REQUIRED =
    new ServiceMessage("E0002", "The state is no more valid, a refresh is required");
  public static final ServiceMessage METRICS_UPDATE_ERROR =
    new ServiceMessage("E0003", "Adding or saving metrics failed for ${" + ErrorMessageConstants.METRICS_NAME + "}");
  public static final ServiceMessage NOSQL_ERROR =
    new ServiceMessage("E0004", "NOSQL server is not running or config error");
  public static final ServiceMessage RESERVATION_NOT_FOUND =
    new ServiceMessage("E0005", NOT_FOUND, "Reservation ${" + ErrorMessageConstants.RESERVATION_ID + "} was not found");
  public static final ServiceMessage ASSET_NOT_FOUND =
    new ServiceMessage("E0006", NOT_FOUND, "Asset ${" + ErrorMessageConstants.ASSET_ID + "} was not found");
  public static final ServiceMessage RESERVATION_ASSET_NOT_FOUND =
    new ServiceMessage("E0007", NOT_FOUND, "Asset ${" + ErrorMessageConstants.ASSET_ID + "} is not assigned to the reservation ${" +
      ErrorMessageConstants.RESERVATION_ID + "}");
  public static final ServiceMessage PATH_VARIABLE_NOT_SET =
    new ServiceMessage("E0008", BAD_REQUEST, "Path variable ${" + ErrorMessageConstants.PATH_VARIABLE_NAME + "} is not set");
  public static final ServiceMessage CONTACT_NOT_FOUND =
    new ServiceMessage("E0012", NOT_FOUND, "Contact ${" + ErrorMessageConstants.CONTACT_EXTERNAL_ID + "} was not found");
  public static final ServiceMessage CONTACT_NOT_EXISTS =
    new ServiceMessage("E0013", INTERNAL_SERVER_ERROR, "Contact ${" + ErrorMessageConstants.CONTACT_ID + "} does not exist in the PS.");
  public static final ServiceMessage DOCUMENT_NOT_FOUND =
    new ServiceMessage("E0014", NOT_FOUND, "Document ${" + ErrorMessageConstants.DOCUMENT_ID + "} was not found");
  public static final ServiceMessage RESERVATION_DOCUMENT_NOT_ASSIGNED =
    new ServiceMessage("E0015", NOT_FOUND, "Reservation ${" + ErrorMessageConstants.RESERVATION_ID + "} and document ${" +
      ErrorMessageConstants.DOCUMENT_ID + "} are not assigned");
  public static final ServiceMessage RESERVATION_CONTACT_NOT_FOUND =
    new ServiceMessage("E0016", NOT_FOUND, "Contact ${" + ErrorMessageConstants.CONTACT_ID + "} is not assigned to the reservation ${" +
      ErrorMessageConstants.RESERVATION_ID + "}");
  public static final ServiceMessage RESERVATION_ASSET_QUANTITY_OVERFLOW =
    new ServiceMessage("E0017", NOT_FOUND, "Quantity is more than the Asset ${" + ErrorMessageConstants.ASSET_ID + "} has");
  public static final ServiceMessage RESERVATION_ASSET_QUANTITY_NULL =
    new ServiceMessage("E0018", NOT_FOUND, "Quantity can not be null");
  public static final ServiceMessage RESERVATION_ASSET_QUANTITY_NEGATIVE =
    new ServiceMessage("E0019", NOT_FOUND, "Quantity can not be less than 0");
  public static final ServiceMessage ASSET_END_DATE_NULL =
    new ServiceMessage("E0020", BAD_REQUEST, "End Date for Asset can not be null");
  public static final ServiceMessage ASSET_END_DATE_IN_PAST =
    new ServiceMessage("E0021", BAD_REQUEST, "End Date for Asset can not be earlier than today");
  public static final ServiceMessage START_DATE_IN_PAST =
    new ServiceMessage("E0022", BAD_REQUEST, "Start date can not be earlier than today");
  public static final ServiceMessage START_AFTER_END =
    new ServiceMessage("E0023", BAD_REQUEST, "Start date must be earlier than end date");
  public static final ServiceMessage RESERVATION_ASSET_END_AFTER_ASSET_END_DATE =
    new ServiceMessage("E0024", BAD_REQUEST, "End date must be earlier than Asset end date");
  public static final ServiceMessage DATE_FORMAT_ERROR =
    new ServiceMessage("E0025", BAD_REQUEST, "Date format must be the following: YYYY-MM-DD, E.g.: 2020-10-02");
  public static final ServiceMessage DATE_NULL =
    new ServiceMessage("E0026", BAD_REQUEST, "Date can not be null");
  public static final ServiceMessage RESERVATION_TO_ASSET_OVERFLOW =
    new ServiceMessage("E0027", BAD_REQUEST, "Asset quantity capacity reached, you can not make reservation to this asset at this time");
}
