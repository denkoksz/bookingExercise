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
import com.rs.client.model.FilterSuggestionItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * FilterSuggestion
 */

public class FilterSuggestion {
  @SerializedName("displayName")
  private String displayName = null;

  @SerializedName("field")
  private String field = null;

  @SerializedName("items")
  private List<FilterSuggestionItem> items = null;

  public FilterSuggestion displayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

   /**
   * Get displayName
   * @return displayName
  **/
  @ApiModelProperty(value = "")
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public FilterSuggestion field(String field) {
    this.field = field;
    return this;
  }

   /**
   * Get field
   * @return field
  **/
  @ApiModelProperty(value = "")
  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public FilterSuggestion items(List<FilterSuggestionItem> items) {
    this.items = items;
    return this;
  }

  public FilterSuggestion addItemsItem(FilterSuggestionItem itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<FilterSuggestionItem>();
    }
    this.items.add(itemsItem);
    return this;
  }

   /**
   * Get items
   * @return items
  **/
  @ApiModelProperty(value = "")
  public List<FilterSuggestionItem> getItems() {
    return items;
  }

  public void setItems(List<FilterSuggestionItem> items) {
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
    FilterSuggestion filterSuggestion = (FilterSuggestion) o;
    return Objects.equals(this.displayName, filterSuggestion.displayName) &&
        Objects.equals(this.field, filterSuggestion.field) &&
        Objects.equals(this.items, filterSuggestion.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(displayName, field, items);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FilterSuggestion {\n");
    
    sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    field: ").append(toIndentedString(field)).append("\n");
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
