package com.rs.repository.service.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rs.api.exception.ServiceException;
import com.rs.message.ErrorMessageConstants;
import com.rs.message.ErrorMessages;
import com.rs.model.Contact;
import com.rs.model.Reservation;
import com.rs.model.ReservationContact;
import com.rs.repository.ReservationContactRepository;
import com.rs.repository.service.ReservationContactRepositoryService;

@Service
public class ReservationContactRepositoryServiceImpl implements ReservationContactRepositoryService {

  private final ReservationContactRepository reservationContactRepository;

  public ReservationContactRepositoryServiceImpl(ReservationContactRepository reservationContactRepository) {
    this.reservationContactRepository = reservationContactRepository;
  }

  @Override
  public ReservationContact create(final Reservation reservation, final Contact contact) {
    return saveReservationContact(new ReservationContact()
      .setReservationId(reservation.getId())
      .setContactId(contact.getId()));
  }

  @Override
  public Optional<ReservationContact> findByReservationAndContact(final Reservation reservation, final Contact contact) {
    return reservationContactRepository.findByReservationIdAndContactId(reservation.getId(), contact.getId());
  }

  @Override
  public ReservationContact getByReservationAndContact(final Reservation reservation, final Contact contact) {
    return reservationContactRepository.findByReservationIdAndContactId(reservation.getId(), contact.getId())
      .orElseThrow(() -> new ServiceException(ErrorMessages.RESERVATION_CONTACT_NOT_FOUND,
        Optional.of(Map.of(ErrorMessageConstants.CONTACT_ID, contact.getExternalId(), ErrorMessageConstants.RESERVATION_ID, reservation
          .getId()))));
  }

  @Override
  public void delete(final ReservationContact reservationContact) {
    reservationContactRepository.delete(reservationContact);
  }

  private ReservationContact saveReservationContact(final ReservationContact reservationContact) {
    return reservationContactRepository.saveAndFlush(reservationContact);
  }
}
