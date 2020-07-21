package com.rs.api.assets.asset.termquantity.freetermquantity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rs.model.TermQuantity;
import com.rs.service.ReservationAssetService;
import com.rs.service.TermQuantityService;
import com.rs.uris.Uris;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class FreeTermQuantityController {

  private final TermQuantityService termQuantityService;

  private final FreeTermQuantityTranslator freeTermQuantityTranslator;

  public FreeTermQuantityController(final TermQuantityService termQuantityService,
                                    final FreeTermQuantityTranslator freeTermQuantityTranslator) {
    this.termQuantityService = termQuantityService;
    this.freeTermQuantityTranslator = freeTermQuantityTranslator;
  }

  @RequestMapping(path = Uris.PS.V1.ASSETS.ASSET.FREETERMQUANTITIES.URI,
    method = GET,
    produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(OK)
  @Transactional
  public FreeTermQuantitiesResponse getFreeTermQuantities(final FreeTermQuantityPathContext freeTermQuantityPathContext) {
    LocalDate startDate = freeTermQuantityPathContext.getStartDate();
    LocalDate endDate = freeTermQuantityPathContext.getEndDate();

    List<TermQuantity> freeTermQuantities = termQuantityService.getFreeTermQuantities(startDate, endDate,
      freeTermQuantityPathContext.getAssetId());

    List<FreeTermQuantityResponse> freeTermQuantityResponses = freeTermQuantityTranslator.translate(freeTermQuantities);

    return new FreeTermQuantitiesResponse(freeTermQuantityResponses, freeTermQuantityResponses.size());
  }
}
