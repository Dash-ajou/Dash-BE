package io.saim.dash.coupon.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;

/**
 * QCoupon is a Querydsl query type for Coupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoupon extends EntityPathBase<Coupon> {

    private static final long serialVersionUID = 1578426059L;

    public static final QCoupon coupon = new QCoupon("coupon");

    public final EnumPath<io.saim.dash.coupon.common.constant.CouponStatus> couponStatus = createEnum("couponStatus", io.saim.dash.coupon.common.constant.CouponStatus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> issueId = createNumber("issueId", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final StringPath registerCode = createString("registerCode");

    public QCoupon(String variable) {
        super(Coupon.class, forVariable(variable));
    }

    public QCoupon(Path<? extends Coupon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCoupon(PathMetadata metadata) {
        super(Coupon.class, metadata);
    }

}

