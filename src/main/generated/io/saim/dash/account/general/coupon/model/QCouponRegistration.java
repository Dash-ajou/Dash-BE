package io.saim.dash.account.general.coupon.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCouponRegistration is a Querydsl query type for CouponRegistration
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCouponRegistration extends EntityPathBase<CouponRegistration> {

    private static final long serialVersionUID = -1265838243L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCouponRegistration couponRegistration = new QCouponRegistration("couponRegistration");

    public final QCoupon coupon;

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final DatePath<java.time.LocalDate> registeredDate = createDate("registeredDate", java.time.LocalDate.class);

    public final NumberPath<Long> registrationId = createNumber("registrationId", Long.class);

    public QCouponRegistration(String variable) {
        this(CouponRegistration.class, forVariable(variable), INITS);
    }

    public QCouponRegistration(Path<? extends CouponRegistration> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCouponRegistration(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCouponRegistration(PathMetadata metadata, PathInits inits) {
        this(CouponRegistration.class, metadata, inits);
    }

    public QCouponRegistration(Class<? extends CouponRegistration> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.coupon = inits.isInitialized("coupon") ? new QCoupon(forProperty("coupon"), inits.get("coupon")) : null;
    }

}

