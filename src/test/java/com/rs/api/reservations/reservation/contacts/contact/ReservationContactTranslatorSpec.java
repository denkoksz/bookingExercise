package com.rs.api.reservations.reservation.contacts.contact;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.AbstractSpec;
import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.api.reservations.reservation.contacts.contact.service.dto.ContactDetailsDTO;
import com.rs.api.reservations.reservation.contacts.contact.service.dto.ContactDetailsMetaDTO;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ReservationContactTranslatorSpec extends AbstractSpec {

  @Autowired
  private ReservationContactTranslator reservationContactTranslator;

  @Test
  public void meta() {
    final ReservationContactResponse translation = reservationContactTranslator.translate(getParams(), contact());

    assertThat(translation.getMeta().getType(), is("Type"));
  }

  @Test
  public void translateProperties() {
    final ReservationContactResponse translation = reservationContactTranslator.translate(getParams(), contact());

    assertThat(translation.getId(), is("contactId"));
    assertThat(translation.getCreated(), is("Created"));
    assertThat(translation.getModified(), is("Modified"));
    assertThat(translation.getName(), is("Name"));
  }

  private ReservationPathContext getParams() {
    return new ReservationPathContext()
      .setApp("app")
      .setReservationId("reservationId");
  }

  private ContactDetailsDTO contact() {
    return new ContactDetailsDTO()
      .setId("contactId")
      .setCreated("Created")
      .setModified("Modified")
      .setName("Name")
      .setMeta(new ContactDetailsMetaDTO().setType("Type"));
  }
}