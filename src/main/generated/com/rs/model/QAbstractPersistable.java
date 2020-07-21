package com.rs.model;

import javax.annotation.Generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringPath;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QAbstractPersistable is a Querydsl query type for AbstractPersistable
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QAbstractPersistable extends EntityPathBase<AbstractPersistable<?>> {

    private static final long serialVersionUID = -1598393049L;

    public static final QAbstractPersistable abstractPersistable = new QAbstractPersistable("abstractPersistable");

    public final DateTimePath<java.util.Date> createdTime = createDateTime("createdTime", java.util.Date.class);

    public final StringPath id = createString("id");

    public final DateTimePath<java.util.Date> updatedTime = createDateTime("updatedTime", java.util.Date.class);

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractPersistable(String variable) {
        super((Class) AbstractPersistable.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractPersistable(Path<? extends AbstractPersistable> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractPersistable(PathMetadata metadata) {
        super((Class) AbstractPersistable.class, metadata);
    }

}

