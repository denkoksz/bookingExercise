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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * The body definition
 */
@ApiModel(description = "The body definition")

public class CreateReservationBody {
  @SerializedName("type")
  private String type = null;

  @SerializedName("title")
  private String title = null;

  public CreateReservationBody type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(required = true, value = "")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public CreateReservationBody title(String title) {
    this.title = title;
    return this;
  }

   /**
   * Get title
   * @return title
  **/
  @ApiModelProperty(value = "")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateReservationBody createReservationBody = (CreateReservationBody) o;
    return Objects.equals(this.type, createReservationBody.type) &&
        Objects.equals(this.title, createReservationBody.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, title);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateReservationBody {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
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

