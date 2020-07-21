package com.rs.repository.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQuery;
import com.rs.model.QReservation;
import com.rs.model.QReservationAsset;
import com.rs.model.Reservation;
import com.rs.repository.custom.ReservationCustomRepository;

public class ReservationRepositoryImpl implements ReservationCustomRepository {

  private static final QReservation RESERVATION = QReservation.reservation;
  private static final QReservationAsset RESERVATION_ASSET = QReservationAsset.reservationAsset;

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Reservation> getReservations(final String assetId) {
    final JPAQuery<Reservation> query = new JPAQuery<>(entityManager);
    query.from(RESERVATION)
      .from(RESERVATION_ASSET)
      .where(RESERVATION.id.eq(RESERVATION_ASSET.reservationId))
      .where(RESERVATION_ASSET.assetId.eq(assetId));
    return query.select(RESERVATION).fetch();
  }
}
