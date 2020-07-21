package com.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.rs.model.ReservationDocument;
import com.rs.repository.custom.ReservationDocumentCustomRepository;

@Repository
public interface ReservationDocumentRepository extends
  ReservationDocumentCustomRepository, JpaRepository<ReservationDocument, String>, QuerydslPredicateExecutor<ReservationDocument> {

}
