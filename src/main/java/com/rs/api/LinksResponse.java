package com.rs.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import static com.rs.api.ResponseConstants.HREF;

public class LinksResponse implements Serializable {

  private final Map<String, String> links;

  private LinksResponse() {
    links = new HashMap<>();
  }

  public static LinksResponse of() {
    return new LinksResponse();
  }

  public LinksResponse addHref(final String link) {
    links.put(HREF, link);
    return this;
  }

  @JsonIgnore
  public Optional<String> getHref() {
    return Optional.of(links.get(HREF));
  }

  @JsonAnyGetter
  public Map<String, String> getProperties() {
    return links;
  }
}
