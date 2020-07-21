package com.rs.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rs.api.exception.ServiceException;
import com.rs.service.AssetEndDateCheckService;

import static com.rs.message.ErrorMessages.ASSET_END_DATE_IN_PAST;
import static com.rs.message.ErrorMessages.ASSET_END_DATE_NULL;
import static java.time.LocalDate.now;

@Service
public class AssetEndDateCheckServiceImpl implements AssetEndDateCheckService {

  @Override
  public LocalDate checkAssetEndDate(final LocalDate assetEndDate) {
    Optional.ofNullable(assetEndDate)
      .or( () -> {
        throw new ServiceException(ASSET_END_DATE_NULL, Optional.empty());
      })
      .filter( value -> value.isAfter(now()) || value.isEqual(now()))
      .or( () -> {
        throw new ServiceException(ASSET_END_DATE_IN_PAST, Optional.empty());
      });
    return assetEndDate;
  }
}
