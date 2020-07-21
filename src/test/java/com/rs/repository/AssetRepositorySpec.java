package com.rs.repository;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.rs.model.Asset;
import com.rs.service.AbstractServiceSpec;

import static com.rs.model.Asset.AssetType.ROOM;
import static com.rs.model.Asset.PartitionType.GROUP;
import static com.rs.model.EntityConsts.PREFIX_ASSET;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class AssetRepositorySpec extends AbstractServiceSpec {

  @Autowired
  AssetRepository assetRepository;

  @Test
  public void assetQuantityNull() {
    DataIntegrityViolationException integrityViolationException = (DataIntegrityViolationException) assertException(() ->
      assetRepository.saveAndFlush(new Asset()
        .setId(idGeneratorService.getId(PREFIX_ASSET))
        .setPartitionType(GROUP)
        .setAssetType(ROOM)));

    assertThat(integrityViolationException.getCause(), instanceOf(ConstraintViolationException.class));
  }

  @Test
  public void assetEndDateNull() {
    DataIntegrityViolationException integrityViolationException = (DataIntegrityViolationException) assertException(() ->
      assetRepository.saveAndFlush(new Asset()
        .setId(idGeneratorService.getId(PREFIX_ASSET))
        .setPartitionType(GROUP)
        .setAssetType(ROOM))
        .setQuantity(1));

    assertThat(integrityViolationException.getCause(), instanceOf(ConstraintViolationException.class));
  }

}
