package io.saim.dash.account.general.coupon.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCoupon is a Querydsl query type for Coupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoupon extends EntityPathBase<Coupon> {

    private static final long serialVersionUID = 1661878404L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCoupon coupon = new QCoupon("coupon");

    public final NumberPath<Long> couponId = createNumber("couponId", Long.class);

    public final StringPath couponNumber = createString("couponNumber");

    public final EnumPath<Coupon.CouponStatus> couponStatus = createEnum("couponStatus", Coupon.CouponStatus.class);

    public final DatePath<java.time.LocalDate> createdDate = createDate("createdDate", java.time.LocalDate.class);

    public final io.saim.dash.account.general.model.QGeneralUser generalUser;

    public final QProduct product;

    public final ListPath<CouponRegistration, QCouponRegistration> registrations = this.<CouponRegistration, QCouponRegistration>createList("registrations", CouponRegistration.class, QCouponRegistration.class, PathInits.DIRECT2);

    public QCoupon(String variable) {
        this(Coupon.class, forVariable(variable), INITS);
    }

    public QCoupon(Path<? extends Coupon> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCoupon(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCoupon(PathMetadata metadata, PathInits inits) {
        this(Coupon.class, metadata, inits);
    }

    public QCoupon(Class<? extends Coupon> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.generalUser = inits.isInitialized("generalUser") ? new io.saim.dash.account.general.model.QGeneralUser(forProperty("generalUser"), inits.get("generalUser")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

