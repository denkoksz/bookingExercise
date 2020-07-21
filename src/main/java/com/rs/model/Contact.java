package com.rs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = EntityConsts.CONTACT_TABLE_NAME)
public class Contact extends AbstractPersistable<Contact> {

  public static String getPrefix() {
    return EntityConsts.PREFIX_CONTACT;
  }

  @Column(name = "partition_id")
  private String partitionId;

  @Column(name = "external_id", unique = true)
  private String externalId;

  public String getExternalId() {
    return externalId;
  }

  public Contact setExternalId(final String externalId) {
    this.externalId = externalId;
    return this;
  }
}
