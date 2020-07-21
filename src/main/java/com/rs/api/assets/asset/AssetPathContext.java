package com.rs.api.assets.asset;

import java.util.Map;

import com.rs.api.BasePathContext;

import static com.rs.api.ContextConstants.VAR_ASSET_ID;

public class AssetPathContext extends BasePathContext<AssetPathContext> {

  private String assetId;

  public AssetPathContext setAssetId(final String assetId) {
    this.assetId = assetId;
    return this;
  }

  @Override
  public Map<String, String> getParams() {
    addParam(VAR_ASSET_ID, assetId);
    return super.getParams();
  }

  public String getAssetId() {
    return assetId;
  }
}
