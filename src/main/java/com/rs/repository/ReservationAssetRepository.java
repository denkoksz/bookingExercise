package com.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.rs.model.ReservationAsset;
import com.rs.repository.custom.ReservationAssetCustomRepository;

@Repository
public interface ReservationAssetRepository extends
  ReservationAssetCustomRepository, JpaRepository<ReservationAsset, String>, QuerydslPredicateExecutor<ReservationAsset> {

}
