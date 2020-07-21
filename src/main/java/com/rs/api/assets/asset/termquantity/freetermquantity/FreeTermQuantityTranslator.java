package com.rs.api.assets.asset.termquantity.freetermquantity;

import java.util.List;

import com.rs.model.TermQuantity;

public interface FreeTermQuantityTranslator {

  List<FreeTermQuantityResponse> translate(List<TermQuantity> freeTermQuantities);
}
