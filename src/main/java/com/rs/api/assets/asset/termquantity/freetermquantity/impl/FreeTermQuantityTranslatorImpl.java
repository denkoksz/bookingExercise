package com.rs.api.assets.asset.termquantity.freetermquantity.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rs.api.assets.asset.termquantity.freetermquantity.FreeTermQuantityResponse;
import com.rs.api.assets.asset.termquantity.freetermquantity.FreeTermQuantityTranslator;
import com.rs.model.TermQuantity;

@Service
public class FreeTermQuantityTranslatorImpl implements FreeTermQuantityTranslator {

  @Override
  public List<FreeTermQuantityResponse> translate(final List<TermQuantity> freeTermQuantities) {
      return freeTermQuantities.stream()
        .map(ftq -> new FreeTermQuantityResponse(
          ftq.getTerm().getStartDate().toString(),
          ftq.getTerm().getEndDate().toString(),
          ftq.getQuantity().longValue()
        )).collect(Collectors.toList());
  }
}
