package com.rs.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.Hibernate;

import static com.rs.model.EntityConsts.CREATED_TIME;
import static com.rs.model.EntityConsts.ID;
import static com.rs.model.EntityConsts.UPDATED_TIME;

@MappedSuperclass
public abstract class AbstractPersistable<T extends AbstractPersistable<T>> {

  @Id
  @Column(name = ID)
  private String id;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, name = CREATED_TIME)
  private Date createdTime;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, name = UPDATED_TIME)
  private Date updatedTime;

  public T setId(final String id) {
    this.id = this.id == null ? id : this.id;
    return (T) this;
  }

  public String getId() {
    return id;
  }

  @PrePersist
  protected void onCreate() {
    createdTime = new Date();
    onUpdate();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedTime = new Date();
  }

  public Date getUpdatedTime() {
    return updatedTime;
  }

  public Date getCreatedTime() {
    return createdTime;
  }

  /**
   * Custom implementation of equals that also works in combination
   * with proxy classes.
   */
  @Override
  public boolean equals(final Object that) {
    return null != that && this == that;
  }
}
