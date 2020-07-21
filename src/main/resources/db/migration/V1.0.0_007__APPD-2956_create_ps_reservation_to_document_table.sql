create table ps_reservation_to_document (
  reservation_id varchar(20) REFERENCES ps_reservation (id),
  document_id varchar(20) REFERENCES ps_reservation_document (id),
  created TIMESTAMP WITHOUT TIME ZONE,
  PRIMARY KEY (reservation_id, document_id)
);