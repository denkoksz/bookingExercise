create table ps_reservation_to_contact (
  reservation_id varchar(20) REFERENCES ps_reservation (id) ,
  contact_id varchar(255) REFERENCES ps_contact (id),
  created TIMESTAMP WITHOUT TIME ZONE,
  PRIMARY KEY (reservation_id, contact_id)
);