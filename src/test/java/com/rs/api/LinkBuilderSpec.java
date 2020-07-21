package com.rs.api;

import org.junit.Test;

import com.rs.api.assets.asset.AssetPathContext;
import com.rs.uris.Uris;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LinkBuilderSpec {

  @Test
  public void buildLink() {
    final AssetPathContext pathContext = new AssetPathContext()
      .setApp("app")
      .setAssetId("assetId");

    final String link = LinkBuilder.buildLink(Uris.PS.V1.ASSETS.ASSET.RESERVATIONS.URI, pathContext);

    assertThat(link, is("/api/v1/app/assets/assetId/reservations"));
  }
}