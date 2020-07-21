package com.rs.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import static com.rs.model.EntityConsts.END_DATE;
import static com.rs.model.EntityConsts.PARTITION_TYPE;
import static com.rs.model.EntityConsts.QUANTITY;
import static com.rs.model.EntityConsts.TYPE;
import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = EntityConsts.ASSET_TABLE_NAME)
public class Asset extends AbstractPersistable<Asset> {

  public enum PartitionType {
    GROUP, SINGLE, OTHER
  }

  public enum AssetType {
    ROOM, APPARTEMENT, TENT
  }

  @Enumerated(STRING)
  @Column(name = PARTITION_TYPE)
  private PartitionType partitionType;

  @Enumerated(STRING)
  @Column(name = TYPE)
  private AssetType assetType;

  @Column(nullable = false, name = QUANTITY)
  private Integer quantity;

  @Column(nullable = false, name = END_DATE, columnDefinition = "varchar(20)")
  private LocalDate endDate;

  public AssetType getAssetType() {
    return assetType;
  }

  public Asset setAssetType(final AssetType assetType) {
    this.assetType = assetType;
    return this;
  }

  public PartitionType getPartitionType() {
    return partitionType;
  }

  public Asset setPartitionType(final PartitionType partitionType) {
    this.partitionType = partitionType;
    return this;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public Asset setQuantity(final Integer quantity) {
    this.quantity = quantity;
    return this;
  }


  public LocalDate getEndDate() {
    return endDate;
  }

  public Asset setEndDate(final LocalDate endDate) {
    this.endDate = Asset.formatEndDate(endDate);
    return this;
  }

  private static LocalDate formatEndDate(LocalDate endDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String date = endDate.format(formatter);
    return LocalDate.parse(date, formatter);
  }
}
