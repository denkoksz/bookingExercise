package com.rs.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rs.model.Asset;
import com.rs.model.TermQuantity;
import com.rs.model.Reservation;
import com.rs.model.ReservationAsset;
import com.rs.model.Term;
import com.rs.repository.service.AssetRepositoryService;
import com.rs.repository.service.ReservationAssetRepositoryService;
import com.rs.repository.service.ReservationRepositoryService;

import static com.rs.model.Asset.AssetType.APPARTEMENT;
import static com.rs.model.Asset.PartitionType.GROUP;
import static com.rs.model.EntityConsts.PREFIX_ASSET;
import static java.time.LocalDate.now;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FreeTermQuantityServiceSpec extends AbstractServiceSpec {

  @Autowired
  private AssetRepositoryService assetService;

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ReservationAssetService reservationAssetService;

  @Autowired
  private TermQuantityService termQuantityService;

  @Autowired
  private ReservationAssetRepositoryService reservationAssetRepositoryService;

  @Autowired
  private IdGeneratorService idGeneratorService;

  private Asset asset;
  private Reservation reservation1;
  private Reservation reservation2;
  private Reservation reservation3;
  private Reservation reservation4;
  private Reservation reservation5;
  private LocalDate inputStart = now();
  private LocalDate inputEnd = now().plusDays(5);
  private String noReservation = "noReservation";

  @Before
  public void init() {
    asset = createAsset();
    reservation1 = reservationService.create("first");
    reservation2 = reservationService.create("second");
    reservation3 = reservationService.create("third");
    reservation4 = reservationService.create("fourth");
    reservation5 = reservationService.create("fifth");
  }

  /*  ASSET QTY: 4
      .  .  .  C  C
      .  .  .  C  C
      .  .  B  B  B
      A  .  B  B  B
   */
  @Test
  public void test1() {
    reservationAssetRepositoryService.create(reservation1, asset, 3, new Term(now(),
      now().plusDays(2)));
    reservationAssetRepositoryService.create(reservation2, asset, 1, new Term(now().plusDays(2),
      now().plusDays(3)));
    reservationAssetRepositoryService.create(reservation3, asset, 1, new Term(now().plusDays(1),
      now().plusDays(2)));

    List<TermQuantity> freeTermQuantities = termQuantityService.getFreeTermQuantities(inputStart, inputEnd, asset.getId());

    List<Term> terms = Arrays.asList(
      new Term(now().plusDays(3), now().plusDays(5)),
      new Term(now().plusDays(2), now().plusDays(5)),
      new Term(now(), now().plusDays(1)));

    int assetQuantityAtGivenInterval = getFullAssetQuantityPerTerm(asset, inputStart, inputEnd);
    int freeAssetQuantities = getFreeAssetQuantities(freeTermQuantities);
    int reservedAssetQuantities = getReservedAssetQuantities(reservationAssetService.getOverLappingReservationAssets(
      new Term(inputStart, inputEnd), asset.getId(), noReservation));

    assertThat(freeAssetQuantities + reservedAssetQuantities, is(assetQuantityAtGivenInterval));
    assertThat(terms.containsAll(getTermsFromTermQuantity(freeTermQuantities)), is(true));
  }

  /*  ASSET QTY: 4
      .  .  .  .  D
      .  .  .  .  D
      .  .  .  .  D
      A  .  .  B  B
  */
  @Test
  public void test2() {
    reservationAssetRepositoryService.create(reservation1, asset, 3, new Term(now(),
      now().plusDays(1)));
    reservationAssetRepositoryService.create(reservation2, asset, 4, new Term(now().plusDays(1),
      now().plusDays(3)));
    reservationAssetRepositoryService.create(reservation3, asset, 3, new Term(now().plusDays(3),
      now().plusDays(4)));

    List<TermQuantity> termQuantities = termQuantityService.getFreeTermQuantities(inputStart, inputEnd, asset.getId());
    termQuantities.forEach(tq -> System.out.println("Date: " + tq.getTerm() + ", QTY: " + tq.getQuantity()));

    List<Term> terms = Arrays.asList(
      new Term(now(), now().plusDays(1)),
      new Term(now().plusDays(3), now().plusDays(5)),
      new Term(now().plusDays(4), now().plusDays(5)));

    int assetQuantityAtGivenInterval = getFullAssetQuantityPerTerm(asset, inputStart, inputEnd);
    int freeAssetQuantities = getFreeAssetQuantities(termQuantities);
    int reservedAssetQuantities = getReservedAssetQuantities(reservationAssetService.getOverLappingReservationAssets(new Term(inputStart,
      inputEnd), asset.getId(), noReservation));

    assertThat(freeAssetQuantities + reservedAssetQuantities, is(assetQuantityAtGivenInterval));
    assertThat(terms.containsAll(getTermsFromTermQuantity(termQuantities)), is(true));
  }

  /*  ASSET QTY: 5
      .  .  .  .  C
      .  .  .  .  C
      .  .  .  .  C
      A  .  B  .  C
  */
  @Test
  public void test3() {
    reservationAssetRepositoryService.create(reservation1, asset, 3, new Term(now(),
      now().plusDays(2)));
    reservationAssetRepositoryService.create(reservation2, asset, 1, new Term(now().plusDays(1),
      now().plusDays(3)));
    reservationAssetRepositoryService.create(reservation3, asset, 3, new Term(now().plusDays(2),
      now().plusDays(4)));
    reservationAssetRepositoryService.create(reservation4, asset, 1, new Term(now().plusDays(3),
      now().plusDays(4)));

    List<TermQuantity> termQuantities = termQuantityService.getFreeTermQuantities(inputStart, inputEnd, asset.getId());

    List<Term> terms = Arrays.asList(
      new Term(now(), now().plusDays(1)),
      new Term(now().plusDays(2), now().plusDays(3)),
      new Term(now().plusDays(4), now().plusDays(5)));

    int assetQuantityAtGivenInterval = getFullAssetQuantityPerTerm(asset, inputStart, inputEnd);
    int freeAssetQuantities = getFreeAssetQuantities(termQuantities);
    int reservedAssetQuantities = getReservedAssetQuantities(reservationAssetService.getOverLappingReservationAssets(new Term(inputStart,
      inputEnd), asset.getId(), noReservation));

    assertThat(freeAssetQuantities + reservedAssetQuantities, is(assetQuantityAtGivenInterval));
    assertThat(terms.containsAll(getTermsFromTermQuantity(termQuantities)), is(true));
  }

  /*  ASSET QTY: 5
      D  D  D  D  .
      C  C  C  .  .
      B  B  .  .  .
      A  .  .  .  .
  */
  @Test
  public void likeACake() {
    reservationAssetRepositoryService.create(reservation2, asset, 1, new Term(now().plusDays(1),
      now().plusDays(2)));
    reservationAssetRepositoryService.create(reservation3, asset, 2, new Term(now().plusDays(2),
      now().plusDays(3)));
    reservationAssetRepositoryService.create(reservation4, asset, 3, new Term(now().plusDays(3),
      now().plusDays(4)));
    reservationAssetRepositoryService.create(reservation5, asset, 4, new Term(now().plusDays(4),
      now().plusDays(5)));

    List<TermQuantity> termQuantities = termQuantityService.getFreeTermQuantities(inputStart, inputEnd, asset.getId());

    List<Term> terms = Arrays.asList(
      new Term(now(), now().plusDays(1)),
      new Term(now(), now().plusDays(2)),
      new Term(now(), now().plusDays(3)),
      new Term(now(), now().plusDays(4)));

    int assetQuantityAtGivenInterval = getFullAssetQuantityPerTerm(asset, inputStart, inputEnd);
    int freeAssetQuantities = getFreeAssetQuantities(termQuantities);
    int reservedAssetQuantities = getReservedAssetQuantities(reservationAssetService.getOverLappingReservationAssets(new Term(inputStart,
      inputEnd), asset.getId(), noReservation));

    assertThat(freeAssetQuantities + reservedAssetQuantities, is(assetQuantityAtGivenInterval));
    assertThat(terms.containsAll(getTermsFromTermQuantity(termQuantities)), is(true));
  }

  @Test
  public void noReservation() {
    List<TermQuantity> termQuantities = termQuantityService.getFreeTermQuantities(inputStart, inputEnd, asset.getId());

    List<Term> terms = Collections.singletonList(
      new Term(inputStart, inputEnd));

    int assetQuantityAtGivenInterval = getFullAssetQuantityPerTerm(asset, inputStart, inputEnd);
    int freeAssetQuantities = getFreeAssetQuantities(termQuantities);
    int reservedAssetQuantities = getReservedAssetQuantities(reservationAssetService.getOverLappingReservationAssets(new Term(inputStart,
      inputEnd), asset.getId(), noReservation));

    assertThat(freeAssetQuantities + reservedAssetQuantities, is(assetQuantityAtGivenInterval));
    assertThat(terms.containsAll(getTermsFromTermQuantity(termQuantities)), is(true));
  }

  /*  ASSET QTY: 5
      .  .  .  .  .
      .  .  .  .  .
      .  .  .  .  .
      .  .  .  .  .
  */
  @Test
  public void FullReservation() {
    reservationAssetRepositoryService.create(reservation1, asset, 4, new Term(now(),
      now().plusDays(5)));

    List<TermQuantity> termQuantities = termQuantityService.getFreeTermQuantities(inputStart, inputEnd, asset.getId());
    assertThat(termQuantities.size(), is(0));
  }

  private Asset createAsset() {
    return assetService.create(idGeneratorService.getId(PREFIX_ASSET), GROUP, APPARTEMENT, 4, now().plusYears(5));
  }

  private int getFreeAssetQuantities(List<TermQuantity> freeAssetTermQuantities) {
    return
      freeAssetTermQuantities.stream()
        .flatMapToInt(fa -> fa.getTerm().getStartDate().datesUntil(fa.getTerm().getEndDate()).collect(Collectors.toList()).stream()
          .mapToInt(date -> fa.getQuantity())).sum();
  }

  private int getReservedAssetQuantities(List<ReservationAsset> reservationAssets) {
    return
      reservationAssets.stream()
        .flatMapToInt(ra -> ra.getTerm().getStartDate().datesUntil(ra.getTerm().getEndDate()).collect(Collectors.toList()).stream()
          .mapToInt(date -> ra.getQuantity())).sum();
  }

  private int getFullAssetQuantityPerTerm(Asset asset, LocalDate start, LocalDate end) {
    List<LocalDate> dates = start.datesUntil(end).collect(Collectors.toList());
    return asset.getQuantity() * dates.size();
  }

  private List<Term> getTermsFromTermQuantity(List<TermQuantity> freeTermQuantities) {
    return freeTermQuantities.stream().map(TermQuantity::getTerm).collect(Collectors.toList());
  }
}
