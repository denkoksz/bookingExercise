package com.rs.repository.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQuery;
import com.rs.model.Asset;
import com.rs.model.QAsset;
import com.rs.model.QReservationAsset;
import com.rs.repository.custom.AssetCustomRepository;

public class AssetRepositoryImpl implements AssetCustomRepository {

  private static final QAsset ASSET = QAsset.asset;
  private static final QReservationAsset RESERVATION_ASSET = QReservationAsset.reservationAsset;

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Asset> getAssets(final String reservationId) {
    final JPAQuery<Asset> query = new JPAQuery<>(entityManager);
    query.from(ASSET)
      .from(RESERVATION_ASSET)
      .where(ASSET.id.eq(RESERVATION_ASSET.assetId))
      .where(RESERVATION_ASSET.reservationId.eq(reservationId));
    return query.select(ASSET).fetch();
  }
}