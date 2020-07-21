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
import com.rs.client.model.ReservationAsset;
import com.rs.client.model.ReservationAssetsMeta;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The assets of a reservation
 */
@ApiModel(description = "The assets of a reservation")

public class ReservationAssets {
  @SerializedName("_meta")
  private ReservationAssetsMeta meta = null;

  @SerializedName("total")
  private Integer total = null;

  @SerializedName("items")
  private List<ReservationAsset> items = null;

  public ReservationAssets meta(ReservationAssetsMeta meta) {
    this.meta = meta;
    return this;
  }

   /**
   * Get meta
   * @return meta
  **/
  @ApiModelProperty(value = "")
  public ReservationAssetsMeta getMeta() {
    return meta;
  }

  public void setMeta(ReservationAssetsMeta meta) {
    this.meta = meta;
  }

  public ReservationAssets total(Integer total) {
    this.total = total;
    return this;
  }

   /**
   * Get total
   * @return total
  **/
  @ApiModelProperty(value = "")
  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public ReservationAssets items(List<ReservationAsset> items) {
    this.items = items;
    return this;
  }

  public ReservationAssets addItemsItem(ReservationAsset itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<ReservationAsset>();
    }
    this.items.add(itemsItem);
    return this;
  }

   /**
   * Get items
   * @return items
  **/
  @ApiModelProperty(value = "")
  public List<ReservationAsset> getItems() {
    return items;
  }

  public void setItems(List<ReservationAsset> items) {
    this.items = items;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReservationAssets reservationAssets = (ReservationAssets) o;
    return Objects.equals(this.meta, reservationAssets.meta) &&
        Objects.equals(this.total, reservationAssets.total) &&
        Objects.equals(this.items, reservationAssets.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(meta, total, items);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservationAssets {\n");
    
    sb.append("    meta: ").append(toIndentedString(meta)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
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
