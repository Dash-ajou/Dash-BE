package io.saim.dash.coupon.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCouponPaymentCode is a Querydsl query type for CouponPaymentCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCouponPaymentCode extends EntityPathBase<CouponPaymentCode> {

    private static final long serialVersionUID = -567320955L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCouponPaymentCode couponPaymentCode = new QCouponPaymentCode("couponPaymentCode");

    public final QCoupon coupon;

    public final DateTimePath<java.time.LocalDateTime> expiresAt = createDateTime("expiresAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> issuedAt = createDateTime("issuedAt", java.time.LocalDateTime.class);

    public final StringPath paymentCode = createString("paymentCode");

    public final NumberPath<Long> paymentCodeId = createNumber("paymentCodeId", Long.class);

    public final StringPath qrCodeUrl = createString("qrCodeUrl");

    public QCouponPaymentCode(String variable) {
        this(CouponPaymentCode.class, forVariable(variable), INITS);
    }

    public QCouponPaymentCode(Path<? extends CouponPaymentCode> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCouponPaymentCode(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCouponPaymentCode(PathMetadata metadata, PathInits inits) {
        this(CouponPaymentCode.class, metadata, inits);
    }

    public QCouponPaymentCode(Class<? extends CouponPaymentCode> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.coupon = inits.isInitialized("coupon") ? new QCoupon(forProperty("coupon"), inits.get("coupon")) : null;
    }

}

