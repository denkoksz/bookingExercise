package com.rs.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QReservationContact is a Querydsl query type for ReservationContact
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReservationContact extends EntityPathBase<ReservationContact> {

    private static final long serialVersionUID = 904969689L;

    public static final QReservationContact reservationContact = new QReservationContact("reservationContact");

    public final StringPath contactId = createString("contactId");

    public final DateTimePath<java.util.Date> created = createDateTime("created", java.util.Date.class);

    public final StringPath reservationId = createString("reservationId");

    public QReservationContact(String variable) {
        super(ReservationContact.class, forVariable(variable));
    }

    public QReservationContact(Path<? extends ReservationContact> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservationContact(PathMetadata metadata) {
        super(ReservationContact.class, metadata);
    }

}

