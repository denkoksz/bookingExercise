package com.rs.api.reservations.reservation.assets;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rs.api.MetaResponse;
import com.rs.api.reservations.reservation.assets.asset.ReservationAssetResponse;

import static com.rs.api.ResponseConstants.ITEMS;
import static com.rs.api.ResponseConstants.META;
import static com.rs.api.ResponseConstants.TOTAL;

@JsonPropertyOrder({META, ITEMS, TOTAL})
public class ReservationAssetsResponse {

  @JsonProperty(META)
  private final MetaResponse meta;

  @JsonProperty(ITEMS)
  private final java.util.List<ReservationAssetResponse> items;

  @JsonProperty(TOTAL)
  private final Integer total;

  private ReservationAssetsResponse(final MetaResponse meta,
                                    final List<ReservationAssetResponse> items,
                                    final int limit,
                                    final int offset) {
    this.meta = meta;
    this.items = items;
    this.total = items.size();
  }

  public static ReservationAssetsResponse of(final MetaResponse meta,
                                             final List<ReservationAssetResponse> items,
                                             final int limit,
                                             final int offset) {
    return new ReservationAssetsResponse(meta, items, limit, offset);
  }

  public MetaResponse getMeta() {
    return meta;
  }

  public java.util.List<ReservationAssetResponse> getItems() {
    return items;
  }

  public Integer getTotal() {
    return total;
  }
}
