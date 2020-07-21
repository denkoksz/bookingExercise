create table ps_asset (
  id varchar(255),
  partition_id varchar(255),
  type_id varchar(255),
  created TIMESTAMP WITHOUT TIME ZONE,
  updated TIMESTAMP WITHOUT TIME ZONE,
  PRIMARY KEY (id)
);