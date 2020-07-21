package com.rs.repository.service;

import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.api.exception.ServiceException;
import com.rs.model.Contact;
import com.rs.service.AbstractServiceSpec;

import static com.rs.message.ErrorMessages.CONTACT_NOT_EXISTS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ContactRepositoryServiceSpec extends AbstractServiceSpec {

  @Autowired
  private ContactRepositoryService contactRepositoryService;

  @Test
  public void create() {
    final Contact createdContact = contactRepositoryService.create("externalId");

    assertThat(createdContact.getExternalId(), is("externalId"));
  }

  @Test
  public void findByExternalIdExists() {
    final Contact createdContact = contactRepositoryService.create("externalId");
    Optional<Contact> contact = contactRepositoryService.findByExternalId(createdContact.getExternalId());

    assertThat(createdContact.getExternalId(), is(contact.get().getExternalId()));
  }

  @Test
  public void findByExternalIdNotExists() {
    Optional<Contact> contact = contactRepositoryService.findByExternalId("UNKNOWN");

    assertThat(contact.isPresent(), is(false));
  }

  @Test
  public void getByIdKnown() {
    final Contact createdContact = contactRepositoryService.create("contactId");
    Contact contact = contactRepositoryService.getById(createdContact.getId());

    assertThat(createdContact.getId(), is(contact.getId()));
  }

  @Test
  public void getByIdUnknown() {
    final ServiceException serviceException = assertServiceException(() -> contactRepositoryService.getById("Unknown"));

    assertThat(serviceException.getServiceMessage().getCode(), is(CONTACT_NOT_EXISTS.getCode()));
  }
}