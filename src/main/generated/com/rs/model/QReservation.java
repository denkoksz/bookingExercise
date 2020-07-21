package com.rs.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QReservation is a Querydsl query type for Reservation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReservation extends EntityPathBase<Reservation> {

    private static final long serialVersionUID = 1788112967L;

    public static final QReservation reservation = new QReservation("reservation");

    public final QAbstractPersistable _super = new QAbstractPersistable(this);

    //inherited
    public final DateTimePath<java.util.Date> createdTime = _super.createdTime;

    //inherited
    public final StringPath id = _super.id;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.util.Date> updatedTime = _super.updatedTime;

    public QReservation(String variable) {
        super(Reservation.class, forVariable(variable));
    }

    public QReservation(Path<? extends Reservation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservation(PathMetadata metadata) {
        super(Reservation.class, metadata);
    }

}

