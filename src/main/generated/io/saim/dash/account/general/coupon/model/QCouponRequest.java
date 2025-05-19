package io.saim.dash.account.general.coupon.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCouponRequest is a Querydsl query type for CouponRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCouponRequest extends EntityPathBase<CouponRequest> {

    private static final long serialVersionUID = 822898027L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCouponRequest couponRequest = new QCouponRequest("couponRequest");

    public final DateTimePath<java.time.LocalDateTime> approvalDate = createDateTime("approvalDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final io.saim.dash.account.partner.model.QPartnerUser partner;

    public final QProduct product;

    public final NumberPath<Integer> requestCount = createNumber("requestCount", Integer.class);

    public final StringPath requestDetail = createString("requestDetail");

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public QCouponRequest(String variable) {
        this(CouponRequest.class, forVariable(variable), INITS);
    }

    public QCouponRequest(Path<? extends CouponRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCouponRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCouponRequest(PathMetadata metadata, PathInits inits) {
        this(CouponRequest.class, metadata, inits);
    }

    public QCouponRequest(Class<? extends CouponRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.partner = inits.isInitialized("partner") ? new io.saim.dash.account.partner.model.QPartnerUser(forProperty("partner")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

