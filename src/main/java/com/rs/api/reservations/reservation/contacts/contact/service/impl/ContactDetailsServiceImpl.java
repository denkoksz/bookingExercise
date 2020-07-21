package com.rs.api.reservations.reservation.contacts.contact.service.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.rs.api.LinkBuilder;
import com.rs.api.exception.ServiceException;
import com.rs.api.reservations.reservation.contacts.contact.ReservationExternalContactContext;
import com.rs.api.reservations.reservation.contacts.contact.service.ContactDetailsService;
import com.rs.api.reservations.reservation.contacts.contact.service.dto.ContactDetailsDTO;
import com.rs.auth.Oauth2ClientConfig;
import com.rs.message.ErrorMessageConstants;
import com.rs.message.ErrorMessages;
import com.rs.uris.ContactUris;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ContactDetailsServiceImpl implements ContactDetailsService {

  @Value("${contact.endpoint}")
  private String contactHost;

  private final OAuth2RestTemplate restTemplate;

  public ContactDetailsServiceImpl(final @Qualifier(Oauth2ClientConfig.CONTACT_CLIENT_CREDENTIALS_QUALIFIER) OAuth2RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public ContactDetailsDTO getContact(final ReservationExternalContactContext context) {
    try {
      return restTemplate
        .getForEntity(getUri(LinkBuilder.buildLink(ContactUris.CONTACT_API.CONTACTS.V1.CONTACT.URI, context)), ContactDetailsDTO.class)
        .getBody();
    } catch (final HttpClientErrorException e) {
      throw new ServiceException(ErrorMessages.CONTACT_NOT_FOUND, Optional.of(Map.of(ErrorMessageConstants.CONTACT_EXTERNAL_ID,
        context.getExternalContactId())));
    }
  }

  private String getUri(final String path) {
    return contactHost + path;
  }
}
