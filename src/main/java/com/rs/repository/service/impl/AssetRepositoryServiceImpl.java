package com.rs.repository.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rs.api.exception.ServiceException;
import com.rs.message.ErrorMessageConstants;
import com.rs.message.ErrorMessages;
import com.rs.model.Asset;
import com.rs.model.Asset.AssetType;
import com.rs.model.Asset.PartitionType;
import com.rs.repository.AssetRepository;
import com.rs.repository.service.AssetRepositoryService;
import com.rs.service.AssetEndDateCheckService;

@Service
public class AssetRepositoryServiceImpl implements AssetRepositoryService {

  private final AssetRepository assetRepository;

  private final AssetEndDateCheckService assetEndDateCheckService;

  public AssetRepositoryServiceImpl(final AssetRepository assetRepository,
                                    final AssetEndDateCheckService assetEndDateCheckService) {
    this.assetRepository = assetRepository;
    this.assetEndDateCheckService = assetEndDateCheckService;
  }

  @Override
  public Asset create(final String id, final PartitionType partitionId, final AssetType type, final Integer quantity, final LocalDate assetEndDate) {
    assetEndDateCheckService.checkAssetEndDate(assetEndDate);
    return saveAsset(new Asset()
      .setId(id)
      .setPartitionType(partitionId)
      .setAssetType(type)
      .setQuantity(quantity)
      .setEndDate(assetEndDate));
  }

  @Override
  public Optional<Asset> findById(final String id) {
    return assetRepository.findById(id);
  }

  @Override
  public Asset getById(final String id) {
    return assetRepository.findById(id)
      .orElseThrow(() -> new ServiceException(ErrorMessages.ASSET_NOT_FOUND, Optional.of(Map.of(ErrorMessageConstants.ASSET_ID, id))));
  }

  @Override
  public List<Asset> getAssets(final String reservationId) {
    return assetRepository.getAssets(reservationId);
  }

  private Asset saveAsset(final Asset asset) {
    return assetRepository.saveAndFlush(asset);
  }
}
