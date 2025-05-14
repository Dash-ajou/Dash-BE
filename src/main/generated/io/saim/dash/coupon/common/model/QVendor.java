package io.saim.dash.coupon.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVendor is a Querydsl query type for Vendor
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVendor extends EntityPathBase<Vendor> {

    private static final long serialVersionUID = 1431027408L;

    public static final QVendor vendor = new QVendor("vendor");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final StringPath presidentName = createString("presidentName");

    public final StringPath presidentPhone = createString("presidentPhone");

    public final ListPath<Request, QRequest> requests = this.<Request, QRequest>createList("requests", Request.class, QRequest.class, PathInits.DIRECT2);

    public final NumberPath<Long> vendorId = createNumber("vendorId", Long.class);

    public final ListPath<UserVendor, QUserVendor> vendorUsers = this.<UserVendor, QUserVendor>createList("vendorUsers", UserVendor.class, QUserVendor.class, PathInits.DIRECT2);

    public QVendor(String variable) {
        super(Vendor.class, forVariable(variable));
    }

    public QVendor(Path<? extends Vendor> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVendor(PathMetadata metadata) {
        super(Vendor.class, metadata);
    }

}

