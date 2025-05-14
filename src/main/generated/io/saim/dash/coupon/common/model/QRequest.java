package io.saim.dash.coupon.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRequest is a Querydsl query type for Request
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRequest extends EntityPathBase<Request> {

    private static final long serialVersionUID = -2134570489L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRequest request = new QRequest("request");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final QDUMMY_PartnerUser partner;

    public final NumberPath<Long> requestId = createNumber("requestId", Long.class);

    public final ListPath<io.saim.dash.coupon.common.model.mapping.RequestProduct, io.saim.dash.coupon.common.model.mapping.QRequestProduct> requestProducts = this.<io.saim.dash.coupon.common.model.mapping.RequestProduct, io.saim.dash.coupon.common.model.mapping.QRequestProduct>createList("requestProducts", io.saim.dash.coupon.common.model.mapping.RequestProduct.class, io.saim.dash.coupon.common.model.mapping.QRequestProduct.class, PathInits.DIRECT2);

    public final EnumPath<io.saim.dash.coupon.common.constant.IssueStatus> status = createEnum("status", io.saim.dash.coupon.common.constant.IssueStatus.class);

    public final QVendor vendor;

    public QRequest(String variable) {
        this(Request.class, forVariable(variable), INITS);
    }

    public QRequest(Path<? extends Request> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRequest(PathMetadata metadata, PathInits inits) {
        this(Request.class, metadata, inits);
    }

    public QRequest(Class<? extends Request> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.partner = inits.isInitialized("partner") ? new QDUMMY_PartnerUser(forProperty("partner")) : null;
        this.vendor = inits.isInitialized("vendor") ? new QVendor(forProperty("vendor")) : null;
    }

}

