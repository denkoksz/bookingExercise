package com.rs.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QReservationDocument is a Querydsl query type for ReservationDocument
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReservationDocument extends EntityPathBase<ReservationDocument> {

    private static final long serialVersionUID = -581538174L;

    public static final QReservationDocument reservationDocument = new QReservationDocument("reservationDocument");

    public final QAbstractPersistable _super = new QAbstractPersistable(this);

    //inherited
    public final DateTimePath<java.util.Date> createdTime = _super.createdTime;

    public final StringPath externalId = createString("externalId");

    //inherited
    public final StringPath id = _super.id;

    public final StringPath title = createString("title");

    public final StringPath type = createString("type");

    //inherited
    public final DateTimePath<java.util.Date> updatedTime = _super.updatedTime;

    public QReservationDocument(String variable) {
        super(ReservationDocument.class, forVariable(variable));
    }

    public QReservationDocument(Path<? extends ReservationDocument> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservationDocument(PathMetadata metadata) {
        super(ReservationDocument.class, metadata);
    }

}

