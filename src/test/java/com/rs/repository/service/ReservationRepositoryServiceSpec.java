package com.rs.repository.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.api.exception.ServiceException;
import com.rs.model.Asset;
import com.rs.model.Reservation;
import com.rs.model.Term;
import com.rs.service.AbstractServiceSpec;

import static com.rs.message.ErrorMessages.RESERVATION_NOT_FOUND;
import static com.rs.model.Asset.AssetType.ROOM;
import static com.rs.model.Asset.PartitionType.GROUP;
import static com.rs.model.EntityConsts.PREFIX_ASSET;
import static java.time.LocalDate.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class ReservationRepositoryServiceSpec extends AbstractServiceSpec {

  @Autowired
  private ReservationRepositoryService reservationRepositoryService;

  @Autowired
  private AssetRepositoryService assetRepositoryService;

  @Autowired
  private ReservationAssetRepositoryService reservationAssetRepositoryService;

  @Test
  public void create() {
    final Reservation first = reservationRepositoryService.create("first");
    final Reservation second = reservationRepositoryService.create("second");

    assertThat(first.getId(), not(second.getId()));
  }

  @Test
  public void getByIdKnown() {
    final Reservation createdReservation = reservationRepositoryService.create("first");

    final Reservation getReservation = reservationRepositoryService.getById(createdReservation.getId());

    assertThat(getReservation, is(createdReservation));
  }

  @Test
  public void getByIdUnknown() {
    final ServiceException serviceException = assertServiceException(() -> reservationRepositoryService.getById("Unknown"));

    assertThat(serviceException.getServiceMessage().getCode(), is(RESERVATION_NOT_FOUND.getCode()));
  }

  @Test
  public void getReservations() {
    final Term term = new Term().setStartDate(now()).setEndDate(now().plusDays(2));
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 1, now().plusDays(5));
    final Reservation reservation = reservationRepositoryService.create("first");
    reservationAssetRepositoryService.create(reservation, asset, 2, term);
    final List<Reservation> reservations = reservationRepositoryService.getReservations(asset.getId());

    assertThat(reservations.size(), is(1));
    assertThat(reservations.get(0).getId(), is(reservation.getId()));
  }
}
