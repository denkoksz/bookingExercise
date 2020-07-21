package com.rs.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAsset is a Querydsl query type for Asset
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAsset extends EntityPathBase<Asset> {

    private static final long serialVersionUID = 626642859L;

    public static final QAsset asset = new QAsset("asset");

    public final QAbstractPersistable _super = new QAbstractPersistable(this);

    public final EnumPath<Asset.AssetType> assetType = createEnum("assetType", Asset.AssetType.class);

    //inherited
    public final DateTimePath<java.util.Date> createdTime = _super.createdTime;

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    //inherited
    public final StringPath id = _super.id;

    public final EnumPath<Asset.PartitionType> partitionType = createEnum("partitionType", Asset.PartitionType.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    //inherited
    public final DateTimePath<java.util.Date> updatedTime = _super.updatedTime;

    public QAsset(String variable) {
        super(Asset.class, forVariable(variable));
    }

    public QAsset(Path<? extends Asset> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAsset(PathMetadata metadata) {
        super(Asset.class, metadata);
    }

}

