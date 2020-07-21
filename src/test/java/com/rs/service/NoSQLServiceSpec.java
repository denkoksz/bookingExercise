package com.rs.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.model.Reservation;
import com.rs.repository.service.ReservationRepositoryService;
import com.rs.service.impl.NoSQLReservation;
import com.rs.service.nosql.NoSQLService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class NoSQLServiceSpec extends AbstractServiceSpec {

  @Autowired
  private NoSQLService noSQLService;

  @Autowired
  private ReservationRepositoryService reservationRepositoryService;

  private Reservation reservation1;
  private Reservation reservation2;
  private Reservation reservation3;
  private Reservation reservation4;
  private Reservation reservation5;
  private NoSQLReservation createdNoSQLReservation1;
  private NoSQLReservation createdNoSQLReservation2;
  private NoSQLReservation createdNoSQLReservation3;
  private NoSQLReservation createdNoSQLReservation4;
  private NoSQLReservation createdNoSQLReservation5;

  @Before
  public void init() {
    noSQLService.clearIndex();
    reservation1 = reservationRepositoryService.create("first");
    reservation2 = reservationRepositoryService.create("first");
    reservation3 = reservationRepositoryService.create("first");
    reservation4 = reservationRepositoryService.create("first");
    reservation5 = reservationRepositoryService.create("first");
    createdNoSQLReservation1 = noSQLService.index(reservation1);
    createdNoSQLReservation2 = noSQLService.index(reservation2);
    createdNoSQLReservation3 = noSQLService.index(reservation3);
    createdNoSQLReservation4 = noSQLService.index(reservation4);
    createdNoSQLReservation5 = noSQLService.index(reservation5);
  }

  @Test
  public void testConstructor() {
    assertThat(noSQLService, notNullValue());
  }

  @Test
  public void index() {
    final NoSQLReservation noSQLReservation = noSQLService.index(reservation1);

    assertThat(reservation1.getId(), is(noSQLReservation.getId()));
    assertThat(reservation1.getCreatedTime().toInstant(), is(noSQLReservation.getCreated().toInstant()));
    assertThat(reservation1.getUpdatedTime().toInstant(), is(noSQLReservation.getLastModified().toInstant()));
  }

  @Test
  public void getById() {
    final NoSQLReservation getByIdNoSQLReservation = noSQLService.getById(reservation1.getId());

    assertThat(reservation1.getId(), is(getByIdNoSQLReservation.getId()));
    assertThat(reservation1.getCreatedTime().toInstant(), is(getByIdNoSQLReservation.getCreated().toInstant()));
    assertThat(reservation1.getUpdatedTime().toInstant(), is(getByIdNoSQLReservation.getLastModified().toInstant()));
  }
}
