package com.rs.repository.custom;

import java.util.Optional;

import com.rs.model.ReservationContact;

public interface ReservationContactCustomRepository {

  Optional<ReservationContact> findByReservationIdAndContactId(String reservationId, String contactId);
}
