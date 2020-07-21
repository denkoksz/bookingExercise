package com.rs.api;

public class LinkBuilder {

  public static <T extends BasePathContext<T>> String buildLink(final String parametrizedUri,
                                                                final BasePathContext<T> basePathContext) {
    return basePathContext.getParams().keySet().stream()
      .reduce(parametrizedUri, (uri, key) -> uri.replace("{" + key + "}", basePathContext.getParams().get(key)));
  }
}
