package com.rs.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QContact is a Querydsl query type for Contact
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QContact extends EntityPathBase<Contact> {

    private static final long serialVersionUID = -1730296293L;

    public static final QContact contact = new QContact("contact");

    public final QAbstractPersistable _super = new QAbstractPersistable(this);

    //inherited
    public final DateTimePath<java.util.Date> createdTime = _super.createdTime;

    public final StringPath externalId = createString("externalId");

    //inherited
    public final StringPath id = _super.id;

    public final StringPath partitionId = createString("partitionId");

    //inherited
    public final DateTimePath<java.util.Date> updatedTime = _super.updatedTime;

    public QContact(String variable) {
        super(Contact.class, forVariable(variable));
    }

    public QContact(Path<? extends Contact> path) {
        super(path.getType(), path.getMetadata());
    }

    public QContact(PathMetadata metadata) {
        super(Contact.class, metadata);
    }

}

