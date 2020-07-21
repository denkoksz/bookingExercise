package com.rs.service;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.api.exception.ServiceException;
import com.rs.model.Asset;
import com.rs.model.Reservation;
import com.rs.model.Term;
import com.rs.repository.service.AssetRepositoryService;
import com.rs.repository.service.ReservationRepositoryService;

import static com.rs.message.ErrorMessages.RESERVATION_TO_ASSET_OVERFLOW;
import static com.rs.model.Asset.AssetType.ROOM;
import static com.rs.model.Asset.PartitionType.GROUP;
import static com.rs.model.EntityConsts.PREFIX_ASSET;
import static java.time.LocalDate.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReservationAssetAvailabilityCheckServiceSpec extends AbstractServiceSpec {

  @Autowired
  private ReservationRepositoryService reservationRepositoryService;

  @Autowired
  private AssetRepositoryService assetRepositoryService;

  @Autowired
  private ReservationAssetService reservationAssetService;

  private Reservation reservation1;
  private Reservation reservation2;
  private Reservation reservation3;
  private Reservation reservation4;
  private Reservation reservation5;

  @Before
  public void init() {
    reservation1 = reservationRepositoryService.create("first");
    reservation2 = reservationRepositoryService.create("second");
    reservation3 = reservationRepositoryService.create("third");
    reservation4 = reservationRepositoryService.create("fourth");
    reservation5 = reservationRepositoryService.create("fifth");
  }

  /*
	Asset  QTY:4
    1. 1  1  1   1   1
    2.    1  1   1   1
    3.       1   1   1
    4.           1   1
    -------------------
    5.    1  1   1   1
       OK OK OK NOK NOK
   */
  @Test
  public void test1() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 4, now().plusYears(1));

    LocalDate start = now();
    LocalDate end = start.plusDays(6);
    Term lastAddedReservationTerm = new Term(start.plusDays(1), end);

    reservationAssetService.addAsset(reservation1.getId(), asset.getId(), 1, new Term(start, end));
    reservationAssetService.addAsset(reservation2.getId(), asset.getId(), 1, new Term(start.plusDays(1), end));
    reservationAssetService.addAsset(reservation3.getId(), asset.getId(), 1, new Term(start.plusDays(2), end));
    reservationAssetService.addAsset(reservation4.getId(), asset.getId(), 1, new Term(start.plusDays(3), end));

    final ServiceException serviceException =
      assertServiceException(() ->
        reservationAssetService.addAsset(reservation5.getId(), asset.getId(), 1, lastAddedReservationTerm)
      );

    assertThat(serviceException.getServiceMessage().getCode(), is(RESERVATION_TO_ASSET_OVERFLOW.getCode()));
  }

  /*
	Asset  QTY:3
    1. 1  1   1   1   1
    2.    2
    3.        2
    4.                2
    --------------------
    5.        1   1
       OK OK NOK  OK  OK
   */
  @Test
  public void testX() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 3, now().plusYears(1));

    LocalDate start = now();
    LocalDate end = start.plusDays(6);

    reservationAssetService.addAsset(reservation1.getId(), asset.getId(), 1, new Term(start, end));
    reservationAssetService.addAsset(reservation2.getId(), asset.getId(), 2, new Term(start.plusDays(1), start.plusDays(2)));
    reservationAssetService.addAsset(reservation3.getId(), asset.getId(), 2, new Term(start.plusDays(2), start.plusDays(3)));
    reservationAssetService.addAsset(reservation4.getId(), asset.getId(), 2, new Term(start.plusDays(5), start.plusDays(6)));

    final ServiceException serviceException =
      assertServiceException(() ->
        reservationAssetService.addAsset(reservation5.getId(), asset.getId(), 1, new Term(start.plusDays(2), start.plusDays(4)))
      );

    assertThat(serviceException.getServiceMessage().getCode(), is(RESERVATION_TO_ASSET_OVERFLOW.getCode()));
  }

  /*
	Asset  QTY:4
    1. 1  1   1   1   1
    2. 1  1   1   1   1
    3. 1  1   1   1   1
    4. 1  1   1   1   1
    ------------------------
    5.                   1  1
       OK OK OK  OK  OK OK OK
   */
  @Test
  public void test2() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 4, now().plusYears(1));

    LocalDate start = now();
    LocalDate end = start.plusDays(6);

    Term term = new Term(start, end);

    reservationAssetService.addAsset(reservation1.getId(), asset.getId(), 1, term);
    reservationAssetService.addAsset(reservation2.getId(), asset.getId(), 1, term);
    reservationAssetService.addAsset(reservation3.getId(), asset.getId(), 1, term);
    reservationAssetService.addAsset(reservation4.getId(), asset.getId(), 1, term);

    Asset lastAddedAsset = reservationAssetService.addAsset(reservation5.getId(), asset.getId(), 1, new Term(end, end.plusDays(2)));

    assertThat(lastAddedAsset.getId(), is(asset.getId()));
  }

  /*
	Asset  QTY:4
    1. 1  1   1   1   1
    2. 1  1   1   1   1
    3. 1  1   1   1   1
    4. 1  1   1   1   1
    -------------------
    5. 1  1   1   1   1
       OK OK OK  OK  OK
   */
  @Test
  public void test3() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 4, now().plusYears(1));

    LocalDate start = now();
    LocalDate end = start.plusDays(6);
    Term term = new Term(start, end);

    reservationAssetService.addAsset(reservation1.getId(), asset.getId(), 1, term);
    reservationAssetService.addAsset(reservation2.getId(), asset.getId(), 1, term);
    reservationAssetService.addAsset(reservation3.getId(), asset.getId(), 1, term);
    reservationAssetService.addAsset(reservation4.getId(), asset.getId(), 1, term);
    reservationAssetService.addAsset(reservation5.getId(), asset.getId(), 1, new Term(start.plusWeeks(1), end.plusWeeks(2)));

    final ServiceException serviceException =
      assertServiceException(() ->
        reservationAssetService.addAsset(reservation5.getId(), asset.getId(), 1, term)
      );

    assertThat(serviceException.getServiceMessage().getCode(), is(RESERVATION_TO_ASSET_OVERFLOW.getCode()));
  }

  /*
	Asset  QTY:4
    1. 3   3   3   3   3
    --------------------
    5. 2   2   2   2   2
      NOK NOK NOK NOK NOK
   */
  @Test
  public void test4() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 4, now().plusYears(1));

    LocalDate start = now();
    LocalDate end = start.plusDays(6);
    Term term = new Term(start, end);

    reservationAssetService.addAsset(reservation1.getId(), asset.getId(), 3, term);

    final ServiceException serviceException =
      assertServiceException(() ->
        reservationAssetService.addAsset(reservation2.getId(), asset.getId(), 2, term));

    assertThat(serviceException.getServiceMessage().getCode(), is(RESERVATION_TO_ASSET_OVERFLOW.getCode()));
  }

  /*
	Asset  QTY:4
    1. 3   3   3   3   3   3
    ---------------------------
    2.         2   2   2   2   2   2
       OK  OK NOK NOK NOK NOK  OK  OK
   */
  @Test
  public void test5() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 4, now().plusYears(1));

    LocalDate start = now();
    LocalDate end = start.plusDays(6);
    Term lastReservationTerm = new Term(start.plusDays(2), end.plusDays(2));

    reservationAssetService.addAsset(reservation1.getId(), asset.getId(), 3, new Term(start, end));
    reservationAssetService.addAsset(reservation2.getId(), asset.getId(), 3, new Term(start.plusWeeks(2), end.plusWeeks(3)));

    final ServiceException serviceException =
      assertServiceException(() ->
        reservationAssetService.addAsset(reservation2.getId(), asset.getId(), 2, lastReservationTerm));

    assertThat(serviceException.getServiceMessage().getCode(), is(RESERVATION_TO_ASSET_OVERFLOW.getCode()));
  }
  /*
	Asset  QTY:4
    1. 3   3   3   3   3  3
    ----------------------------
    2.                       4  4
       OK  OK OK  OK  OK  OK OK OK
   */
  @Test
  public void test6() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 4, now().plusYears(1));

    LocalDate start = now();
    LocalDate end = start.plusDays(6);

    Term assetStartedOnEndDateTerm = new Term(end, end.plusDays(2));

    reservationAssetService.addAsset(reservation1.getId(), asset.getId(), 3, new Term(start, end));
    Asset assetStartedOnEndDate = reservationAssetService.addAsset(reservation2.getId(), asset.getId(), 4, assetStartedOnEndDateTerm);

    assertThat(assetStartedOnEndDate.getId(), is(asset.getId()));
  }

  /*
	Asset  QTY:4
    1.             3    3   3
    2. 1   1   1   1
    ----------------------------
    3.     2   2   2    2
       OK  OK OK  NOK  NOK  OK OK
   */
  @Test
  public void test7() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 4, now().plusYears(1));

    reservationAssetService.addAsset(reservation1.getId(), asset.getId(), 3, new Term(now().plusDays(3), now().plusDays(6)));
    reservationAssetService.addAsset(reservation2.getId(), asset.getId(), 1, new Term(now(), now().plusDays(4)));

    Term termLastReservation = new Term(now().plusDays(1), now().plusDays(5));

    final ServiceException serviceException =
      assertServiceException(() ->
        reservationAssetService.addAsset(reservation3.getId(), asset.getId(), 2, termLastReservation));

    assertThat(serviceException.getServiceMessage().getCode(), is(RESERVATION_TO_ASSET_OVERFLOW.getCode()));
  }

  /*
	Asset  QTY:3
    1. 1   1   1   1   1   1
    2.     1
    3.         1
    4.             1
    ------------------------
    5.     1   1   1   1   1
       OK  OK  OK  OK  OK  OK
   */
  @Test
  public void test8() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 3, now().plusYears(1));

    LocalDate start = now();
    LocalDate end = start.plusDays(6);

    reservationAssetService.addAsset(reservation1.getId(), asset.getId(), 1, new Term(start, end));
    reservationAssetService.addAsset(reservation2.getId(), asset.getId(), 1, new Term(start.plusDays(1), start.plusDays(2)));
    reservationAssetService.addAsset(reservation3.getId(), asset.getId(), 1, new Term(start.plusDays(2), start.plusDays(3)));
    reservationAssetService.addAsset(reservation4.getId(), asset.getId(), 1, new Term(start.plusDays(3), start.plusDays(4)));

    Term lastAddedReservationTerm = new Term(start.plusDays(1), end);

    Asset lastAddedAsset = reservationAssetService.addAsset(reservation5.getId(), asset.getId(), 1, lastAddedReservationTerm);

    assertThat(lastAddedAsset.getId(), is(asset.getId()));
  }

  /*
  Asset  QTY:3
  1.             1
  2.         1   1   1
  3.     1   1   1   1   1
  ----------------------------
  4. 1   1   1   1   1   1   1
     OK  OK  OK NOK  OK  OK  OK
 */
  @Test
  public void likeACake() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 3, now().plusYears(1));


    reservationAssetService.addAsset(reservation1.getId(), asset.getId(), 1, new Term(now().plusDays(3), now().plusDays(4)));
    reservationAssetService.addAsset(reservation2.getId(), asset.getId(), 1, new Term(now().plusDays(2), now().plusDays(5)));
    reservationAssetService.addAsset(reservation3.getId(), asset.getId(), 1, new Term(now().plusDays(1), now().plusDays(6)));

    final ServiceException serviceException =
      assertServiceException(() ->
           reservationAssetService.addAsset(reservation4.getId(), asset.getId(), 1, new Term(now(), now().plusDays(7))));

    assertThat(serviceException.getServiceMessage().getCode(), is(RESERVATION_TO_ASSET_OVERFLOW.getCode()));
  }

  /*
  Asset  QTY:4
  1.  2   2
  2.  1
  3.           3
  4.                   3   3
  ---------------------------
  5.  2   2    2   2   2   2
     NOK  OK  NOK OK  NOK NOK
 */
  @Test
  public void test9() {
    final Asset asset = assetRepositoryService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, ROOM, 4, now().plusYears(1));


    reservationAssetService.addAsset(reservation1.getId(), asset.getId(), 2, new Term(now(), now().plusDays(2)));
    reservationAssetService.addAsset(reservation2.getId(), asset.getId(), 1, new Term(now(), now().plusDays(1)));
    reservationAssetService.addAsset(reservation3.getId(), asset.getId(), 3, new Term(now().plusDays(2), now().plusDays(3)));
    reservationAssetService.addAsset(reservation4.getId(), asset.getId(), 3, new Term(now().plusDays(4), now().plusDays(6)));

    final ServiceException serviceException =
      assertServiceException(() ->
        reservationAssetService.addAsset(reservation5.getId(), asset.getId(), 2, new Term(now(), now().plusDays(6))));

    assertThat(serviceException.getServiceMessage().getCode(), is(RESERVATION_TO_ASSET_OVERFLOW.getCode()));
  }

}
