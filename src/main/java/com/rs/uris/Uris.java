package com.rs.uris;

import static com.rs.api.ContextConstants.APP;
import static com.rs.api.ContextConstants.VAR_ASSET_ID;
import static com.rs.api.ContextConstants.VAR_DOCUMENT_ID;
import static com.rs.api.ContextConstants.VAR_EXTERNAL_ID;
import static com.rs.api.ContextConstants.VAR_RESERVATION_ID;

public class Uris {

  private static final String ROOT_PATH = "/";
  private static final String PS_V1_BASE_PATH = ROOT_PATH + "api/v1";

  private Uris() {
  }

  public static class PS {

    public static final String URI = ROOT_PATH;

    public static class V1 {

      public static final String URI = PS_V1_BASE_PATH + "/{" + APP + "}";

      public static final class ASSETS {

        public static final String URI = V1.URI + "/assets";

        public static final class ASSET {

          public static final String URI = ASSETS.URI + "/{" + VAR_ASSET_ID + "}";

          public static final class FREETERMQUANTITIES {

            public static final String URI = ASSET.URI + "/freetermquantities";
          }

          public static final class RESERVATIONS {

            public static final String URI = ASSET.URI + "/reservations";
          }
        }
      }

      public static final class RESERVATIONS {

        public static final String URI = V1.URI + "/reservations";

        public static final class RESERVATION {

          public static final String URI = RESERVATIONS.URI + "/{" + VAR_RESERVATION_ID + "}";

          public static final class ASSETS {

            public static final String URI = RESERVATION.URI + "/assets";

            public static final class ASSET {

              public static final String URI = ASSETS.URI + "/{" + VAR_ASSET_ID + "}";
            }
          }

          public static final class CONTACTS {

            public static final String URI = RESERVATION.URI + "/contacts";

            public static final class CONTACT {

              public static final String URI = CONTACTS.URI + "/{" + VAR_EXTERNAL_ID + "}";
            }
          }

          public static final class DOCUMENTS {

            public static final String URI = RESERVATION.URI + "/documents";

            public static final class DOCUMENT {

              public static final String URI = DOCUMENTS.URI + "/{" + VAR_DOCUMENT_ID + "}";
            }
          }
        }
      }

      public static final class FACETS {

        public static final String URI = "facets";
      }

      public static final class SUGGESTIONS {

        public static final String URI = "suggestions";
      }
    }
  }
}
