package com.rs.repository.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.rs.model.Asset;

public interface AssetRepositoryService {

  Asset create(String id, Asset.PartitionType partitionId, Asset.AssetType type, Integer quantity, LocalDate assetEndDate);

  Optional<Asset> findById(String id);

  Asset getById(String id);

  List<Asset> getAssets(String reservationId);
}
