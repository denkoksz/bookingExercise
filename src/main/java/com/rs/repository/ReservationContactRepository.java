package com.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.rs.model.ReservationContact;
import com.rs.repository.custom.ReservationContactCustomRepository;

@Repository
public interface ReservationContactRepository extends
  ReservationContactCustomRepository, JpaRepository<ReservationContact, String>, QuerydslPredicateExecutor<ReservationContact> {

}
