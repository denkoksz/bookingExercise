package com.rs.api.assets.asset.termquantity.freetermquantity;

import java.time.LocalDate;

import com.rs.api.BasePathContext;

import static com.rs.api.ContextConstants.END_DATE;
import static com.rs.api.ContextConstants.START_DATE;
import static com.rs.api.ContextConstants.VAR_ASSET_ID;

public class FreeTermQuantityPathContext extends BasePathContext<FreeTermQuantityPathContext> {

  public FreeTermQuantityPathContext setStartDate(final String startDate) {
    super.addParam(START_DATE, startDate);
    return this;
  }

  public FreeTermQuantityPathContext setEndDate(final String endDate) {
    super.addParam(END_DATE, endDate);
    return this;
  }

  public FreeTermQuantityPathContext setAssetId(String assetId) {
    super.addParam(VAR_ASSET_ID, assetId);
    return this;
  }

  public String getAssetId() {
    return getParam(VAR_ASSET_ID);
  }

  public LocalDate getStartDate() {
    return LocalDate.parse(getParam(START_DATE));
  }

  public LocalDate getEndDate() {
    return LocalDate.parse(getParam(END_DATE));
  }

}
