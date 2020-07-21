package com.rs.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rs.uris.Uris;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class RootResource {

  @RequestMapping(path = Uris.PS.URI, method = GET, produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(OK)
  public RootResponse getStatus() {
    return RootResponse.of("ES Reservation Service is up and running! :)");
  }
}
