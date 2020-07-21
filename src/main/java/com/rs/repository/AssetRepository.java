package com.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.rs.model.Asset;
import com.rs.repository.custom.AssetCustomRepository;

@Repository
public interface AssetRepository extends AssetCustomRepository, JpaRepository<Asset, String>, QuerydslPredicateExecutor<Asset> {

}
