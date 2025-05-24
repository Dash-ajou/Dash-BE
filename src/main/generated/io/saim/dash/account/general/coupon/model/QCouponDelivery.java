package io.saim.dash.account.general.coupon.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCouponDelivery is a Querydsl query type for CouponDelivery
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCouponDelivery extends EntityPathBase<CouponDelivery> {

    private static final long serialVersionUID = 956759192L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCouponDelivery couponDelivery = new QCouponDelivery("couponDelivery");

    public final io.saim.dash.coupon.common.model.QCoupon coupon;

    public final NumberPath<Long> deliveryId = createNumber("deliveryId", Long.class);

    public final NumberPath<Long> receiverId = createNumber("receiverId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> requestedAt = createDateTime("requestedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> senderId = createNumber("senderId", Long.class);

    public final EnumPath<CouponDelivery.DeliveryStatus> status = createEnum("status", CouponDelivery.DeliveryStatus.class);

    public QCouponDelivery(String variable) {
        this(CouponDelivery.class, forVariable(variable), INITS);
    }

    public QCouponDelivery(Path<? extends CouponDelivery> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCouponDelivery(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCouponDelivery(PathMetadata metadata, PathInits inits) {
        this(CouponDelivery.class, metadata, inits);
    }

    public QCouponDelivery(Class<? extends CouponDelivery> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.coupon = inits.isInitialized("coupon") ? new io.saim.dash.coupon.common.model.QCoupon(forProperty("coupon"), inits.get("coupon")) : null;
    }

}

