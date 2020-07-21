package com.rs.model;

public class EntityConsts {

  public final static String PREFIX_ASSET = "AA";
  public final static String PREFIX_CONTACT = "RC";
  public final static String PREFIX_RESERVATION = "RS";
  public static final String PREFIX_RESERVATION_DOCUMENT = "RD";

  static final String CONTACT_TABLE_NAME = "rs_contact";
  static final String RESERVATION_CONTACT_TABLE_NAME = "rs_reservation_to_contact";
  static final String RESERVATION_TABLE_NAME = "rs_reservation";
  static final String ASSET_TABLE_NAME = "rs_asset";
  static final String RESERVATION_ASSET_TABLE_NAME = "rs_reservation_to_asset";
  static final String RESERVATION_DOCUMENT_TABLE_NAME = "rs_reservation_document";
  static final String RESERVATION_TO_DOCUMENT_TABLE_NAME = "rs_reservation_to_document";

  public static final String ASSET_ID = "asset_id";
  public static final String CONTACT_ID = "contact_id";
  public static final String CREATED_TIME = "created_time";
  public static final String DOCUMENT_ID = "document_id";
  public static final String END_DATE = "end_date";
  public static final String EXTERNAL_ID = "external_id";
  public static final String ID = "id";
  public static final String QUANTITY = "quantity";
  public static final String PARTITION_TYPE = "partition_type";
  public static final String RESERVATION_ID = "reservation_id";
  public static final String START_DATE = "start_date";
  public static final String TITLE = "title";
  public static final String TYPE = "type";
  public static final String UPDATED_TIME = "updated_time";
}