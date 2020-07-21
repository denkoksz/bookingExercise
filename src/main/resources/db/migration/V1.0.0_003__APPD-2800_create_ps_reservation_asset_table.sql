create table ps_reservation_to_asset (
  reservation_id varchar(20),
  asset_id varchar(255),
  created TIMESTAMP WITHOUT TIME ZONE,
  PRIMARY KEY (reservation_id, asset_id)
);