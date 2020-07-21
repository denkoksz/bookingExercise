create table ps_reservation_document (
  id varchar(20),
  external_id varchar(255) UNIQUE,
  type varchar(50),
  title varchar(50),
  created TIMESTAMP WITHOUT TIME ZONE,
  updated TIMESTAMP WITHOUT TIME ZONE,
  PRIMARY KEY (id)
);