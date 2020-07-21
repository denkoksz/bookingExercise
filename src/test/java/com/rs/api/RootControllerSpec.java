package com.rs.api;

import org.junit.Test;

import com.rs.client.ApiException;
import com.rs.client.model.Root;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RootControllerSpec extends AbstractApiSpec {

  @Test
  public void statusEndpoint() throws ApiException {
    final Root root = rootGet();

    assertThat(root.getStatus(), is("ES Reservation Service is up and running! :)"));
  }
}
