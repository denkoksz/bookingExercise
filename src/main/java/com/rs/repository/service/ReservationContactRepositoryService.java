package com.rs.repository.service;

import java.util.Optional;

import com.rs.model.Contact;
import com.rs.model.Reservation;
import com.rs.model.ReservationContact;

public interface ReservationContactRepositoryService {

  ReservationContact create(Reservation reservation, Contact contact);

  Optional<ReservationContact> findByReservationAndContact(Reservation reservation, Contact contact);

  ReservationContact getByReservationAndContact(Reservation reservation, Contact contact);

  void delete(ReservationContact reservationContact);
}
