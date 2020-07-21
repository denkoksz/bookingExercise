package com.rs.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QReservationToDocument is a Querydsl query type for ReservationToDocument
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReservationToDocument extends EntityPathBase<ReservationToDocument> {

    private static final long serialVersionUID = 1144158301L;

    public static final QReservationToDocument reservationToDocument = new QReservationToDocument("reservationToDocument");

    public final DateTimePath<java.util.Date> createdTime = createDateTime("createdTime", java.util.Date.class);

    public final StringPath documentId = createString("documentId");

    public final StringPath reservationId = createString("reservationId");

    public QReservationToDocument(String variable) {
        super(ReservationToDocument.class, forVariable(variable));
    }

    public QReservationToDocument(Path<? extends ReservationToDocument> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservationToDocument(PathMetadata metadata) {
        super(ReservationToDocument.class, metadata);
    }

}

