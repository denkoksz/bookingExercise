package com.rs;

import java.time.LocalDate;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rs.model.Asset;
import com.rs.repository.service.AssetRepositoryService;

import static com.rs.RSProfile.LOCAL;
import static java.time.LocalDate.now;

@Component
@Profile(LOCAL)
public class DataLoader {

  private AssetRepositoryService assetRepositoryService;

  @Autowired
  public DataLoader(AssetRepositoryService assetRepositoryService) {
    this.assetRepositoryService = assetRepositoryService;
  }

  @Transactional
  @PostConstruct
  public void loadAssets() {
    LocalDate assetEndDate = now().plusYears(1);
    assetRepositoryService.create("TEST1", Asset.PartitionType.SINGLE, Asset.AssetType.ROOM, 10, assetEndDate);
    assetRepositoryService.create("TEST2", Asset.PartitionType.SINGLE, Asset.AssetType.ROOM, 20, assetEndDate);
    assetRepositoryService.create("TEST3", Asset.PartitionType.SINGLE, Asset.AssetType.ROOM, 30, assetEndDate);
  }
}
