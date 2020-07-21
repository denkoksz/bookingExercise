package com.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.rs.model.Contact;
import com.rs.repository.custom.ContactCustomRepository;

@Repository
public interface ContactRepository extends ContactCustomRepository, JpaRepository<Contact, String>, QuerydslPredicateExecutor<Contact> {

}
