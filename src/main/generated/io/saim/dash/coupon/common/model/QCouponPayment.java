package io.saim.dash.coupon.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCouponPayment is a Querydsl query type for CouponPayment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCouponPayment extends EntityPathBase<CouponPayment> {

    private static final long serialVersionUID = 1783642232L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCouponPayment couponPayment = new QCouponPayment("couponPayment");

    public final QCoupon coupon;

    public final DateTimePath<java.time.LocalDateTime> expiredAt = createDateTime("expiredAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> generatedAt = createDateTime("generatedAt", java.time.LocalDateTime.class);

    public final StringPath paymentCode = createString("paymentCode");

    public QCouponPayment(String variable) {
        this(CouponPayment.class, forVariable(variable), INITS);
    }

    public QCouponPayment(Path<? extends CouponPayment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCouponPayment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCouponPayment(PathMetadata metadata, PathInits inits) {
        this(CouponPayment.class, metadata, inits);
    }

    public QCouponPayment(Class<? extends CouponPayment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.coupon = inits.isInitialized("coupon") ? new QCoupon(forProperty("coupon"), inits.get("coupon")) : null;
    }

}

