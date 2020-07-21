package com.rs.uris;

import com.rs.api.ContextConstants;

public class ContactUris {

  private static final String API = "/api";
  private static final String CONTACTS_MODULE = "/contacts";
  private static final String VERSION1 = "/v1";
  private static final String APP = "/{" + ContextConstants.APP + "}";

  public static class CONTACT_API {

    private static final String URI = API;

    public static class CONTACTS {

      private static final String URI = CONTACT_API.URI + CONTACTS_MODULE;

      public static final class V1 {

        private static final String URI = CONTACTS.URI + VERSION1 + APP + "/contacts";

        public static final class CONTACT {

          public static final String URI = V1.URI + "/{" + ContextConstants.VAR_CONTACT_ID + "}";
        }
      }
    }
  }
}
