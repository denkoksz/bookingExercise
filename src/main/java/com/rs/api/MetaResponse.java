package com.rs.api;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import static com.rs.api.ResponseConstants.LINKS;

@JsonPropertyOrder({LINKS})
public class MetaResponse {

  @JsonProperty(LINKS)
  private final Map<String, LinksResponse> links;

  protected MetaResponse() {
    links = new HashMap<>();
  }

  public static MetaResponse empty() {
    return new MetaResponse();
  }

  public MetaResponse addLink(final String label, final String link) {
    links.put(label, LinksResponse.of().addHref(link));
    return this;
  }

  public String getLink(final String label) {
    return links.get(label).getHref().orElseGet(() -> "");
  }
}
