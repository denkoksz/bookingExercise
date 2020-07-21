package com.rs.repository.custom;

import java.util.List;

import com.rs.model.Asset;

public interface AssetCustomRepository {

  List<Asset> getAssets(String reservationId);
}
