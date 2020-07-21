package com.rs.api.reservations.reservation.contacts;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.AbstractSpec;
import com.rs.api.reservations.reservation.ReservationPathContext;
import com.rs.api.reservations.reservation.contacts.contact.ReservationContactResponse;
import com.rs.api.reservations.reservation.contacts.contact.service.dto.ContactDetailsDTO;
import com.rs.api.reservations.reservation.contacts.contact.service.dto.ContactDetailsMetaDTO;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ReservationContactsTranslatorSpec extends AbstractSpec {

  @Autowired
  private ReservationContactsTranslator reservationContactsTranslator;

  @Test
  public void translate() {
    final ReservationContactsResponse translation =
      reservationContactsTranslator.translate(ReservationPathContext.of("app", "reservationId"), contacts());
    final ReservationContactResponse item = translation.getItems().get(0);

    assertThat(translation.getMeta().getLink("self"), is("/api/v1/app/reservations/reservationId/contacts"));
    assertThat(translation.getTotal(), is(1));
    assertThat(item.getMeta().getType(), is("individual"));
    assertThat(item.getId(), is("Id"));
    assertThat(item.getName(), is("Name"));
    assertThat(item.getModified(), is("Modified"));
    assertThat(item.getCreated(), is("Created"));
  }

  private List<ContactDetailsDTO> contacts() {
    return List.of(new ContactDetailsDTO()
      .setMeta(new ContactDetailsMetaDTO().setType("individual"))
      .setId("Id")
      .setName("Name")
      .setModified("Modified")
      .setCreated("Created"));
  }
}