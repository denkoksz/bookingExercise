package com.rs.repository.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rs.api.exception.ServiceException;
import com.rs.model.Contact;
import com.rs.repository.ContactRepository;
import com.rs.repository.service.ContactRepositoryService;
import com.rs.service.IdGeneratorService;

import static com.rs.message.ErrorMessageConstants.CONTACT_EXTERNAL_ID;
import static com.rs.message.ErrorMessageConstants.CONTACT_ID;
import static com.rs.message.ErrorMessages.CONTACT_NOT_EXISTS;
import static com.rs.message.ErrorMessages.CONTACT_NOT_FOUND;

@Service
public class ContactRepositoryServiceImpl implements ContactRepositoryService {

  private final ContactRepository contactRepository;

  private final IdGeneratorService idGeneratorService;

  public ContactRepositoryServiceImpl(ContactRepository contactRepository, IdGeneratorService idGeneratorService) {
    this.contactRepository = contactRepository;
    this.idGeneratorService = idGeneratorService;
  }

  @Override
  public Optional<Contact> findByExternalId(final String externalContactId) {
    return contactRepository.findByExternalId(externalContactId);
  }

  @Override
  public Contact getById(final String contactId) {
    return contactRepository.findById(contactId)
      .orElseThrow(() -> new ServiceException(CONTACT_NOT_EXISTS, Optional.of(Map.of(CONTACT_ID, contactId))));
  }

  @Override
  public Contact getByExternalId(final String externalId) {
    return findByExternalId(externalId)
      .orElseThrow(() -> new ServiceException(CONTACT_NOT_FOUND, Optional.of(Map.of(CONTACT_EXTERNAL_ID, externalId))));
  }

  @Override
  public List<Contact> getContactsOfAReservation(final String reservationId) {
    return contactRepository.getContactsOfAReservation(reservationId);
  }

  @Override
  public Contact create(final String externalId) {
    return saveContact(new Contact()
      .setId(idGeneratorService.getId(Contact.getPrefix()))
      .setExternalId(externalId));
  }

  private Contact saveContact(final Contact contact) {
    return contactRepository.saveAndFlush(contact);
  }
}
