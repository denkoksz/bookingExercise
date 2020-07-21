package com.rs.repository.impl;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQuery;
import com.rs.model.Asset;
import com.rs.model.QReservationAsset;
import com.rs.model.ReservationAsset;
import com.rs.model.Term;
import com.rs.repository.custom.ReservationAssetCustomRepository;

public class ReservationAssetRepositoryImpl implements ReservationAssetCustomRepository {

  private static final QReservationAsset RESERVATION_ASSET = QReservationAsset.reservationAsset;

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<ReservationAsset> findByReservationIdAndAssetId(final String reservationId, final String assetId) {
    final JPAQuery<Asset> query = new JPAQuery<>(entityManager);
    query.from(RESERVATION_ASSET)
      .where(RESERVATION_ASSET.reservationId.eq(reservationId))
      .where(RESERVATION_ASSET.assetId.eq(assetId));
    return Optional.ofNullable(query.select(RESERVATION_ASSET).fetchFirst());
  }

  @Override
  public List<ReservationAsset> getOverLappingReservationAssets(final Term term, final String assetId, final String reservationId) {
    final JPAQuery<Asset> query = new JPAQuery<>(entityManager);
    query.from(RESERVATION_ASSET)
      .where(RESERVATION_ASSET.assetId.eq(assetId).and(RESERVATION_ASSET.reservationId.ne(reservationId)))
      .where(RESERVATION_ASSET.endDate.gt(term.getStartDate()))
      .where(RESERVATION_ASSET.startDate.lt(term.getEndDate()));
    return query.select(RESERVATION_ASSET).fetch();
  }
}

