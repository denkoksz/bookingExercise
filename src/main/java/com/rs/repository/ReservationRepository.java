package com.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.rs.model.Reservation;
import com.rs.repository.custom.ReservationCustomRepository;

@Repository
public interface ReservationRepository extends
  ReservationCustomRepository, JpaRepository<Reservation, String>, QuerydslPredicateExecutor<Reservation> {

}
