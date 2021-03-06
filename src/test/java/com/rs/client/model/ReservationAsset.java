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


package com.rs.client.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.rs.client.model.ReservationAssetMeta;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * An asset that is part of a reservation
 */
@ApiModel(description = "An asset that is part of a reservation")

public class ReservationAsset {
  @SerializedName("_meta")
  private ReservationAssetMeta meta = null;

  @SerializedName("id")
  private String id = null;

  public ReservationAsset meta(ReservationAssetMeta meta) {
    this.meta = meta;
    return this;
  }

   /**
   * Get meta
   * @return meta
  **/
  @ApiModelProperty(value = "")
  public ReservationAssetMeta getMeta() {
    return meta;
  }

  public void setMeta(ReservationAssetMeta meta) {
    this.meta = meta;
  }

  public ReservationAsset id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReservationAsset reservationAsset = (ReservationAsset) o;
    return Objects.equals(this.meta, reservationAsset.meta) &&
        Objects.equals(this.id, reservationAsset.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(meta, id);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservationAsset {\n");
    
    sb.append("    meta: ").append(toIndentedString(meta)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

