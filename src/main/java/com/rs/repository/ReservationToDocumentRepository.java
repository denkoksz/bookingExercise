package com.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.rs.model.ReservationToDocument;
import com.rs.repository.custom.ReservationToDocumentCustomRepository;

@Repository
public interface ReservationToDocumentRepository extends
  ReservationToDocumentCustomRepository, JpaRepository<ReservationToDocument, String>, QuerydslPredicateExecutor<ReservationToDocument> {

}
