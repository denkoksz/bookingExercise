package com.rs.model;

import javax.annotation.Generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QReservationAsset is a Querydsl query type for ReservationAsset
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReservationAsset extends EntityPathBase<ReservationAsset> {

    private static final long serialVersionUID = -1381785879L;

    public static final QReservationAsset reservationAsset = new QReservationAsset("reservationAsset");

    public final StringPath assetId = createString("assetId");

    public final DateTimePath<java.util.Date> created = createDateTime("created", java.util.Date.class);

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final StringPath reservationId = createString("reservationId");

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public QReservationAsset(String variable) {
        super(ReservationAsset.class, forVariable(variable));
    }

    public QReservationAsset(Path<? extends ReservationAsset> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservationAsset(PathMetadata metadata) {
        super(ReservationAsset.class, metadata);
    }

}

