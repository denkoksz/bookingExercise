create table ps_contact (
  id varchar(20),
  partition_id varchar(255),
  external_id varchar(255),
  created TIMESTAMP WITHOUT TIME ZONE,
  updated TIMESTAMP WITHOUT TIME ZONE,
  PRIMARY KEY (id)
);