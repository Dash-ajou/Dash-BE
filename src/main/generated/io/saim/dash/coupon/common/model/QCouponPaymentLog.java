package io.saim.dash.coupon.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCouponPaymentLog is a Querydsl query type for CouponPaymentLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCouponPaymentLog extends EntityPathBase<CouponPaymentLog> {

    private static final long serialVersionUID = -849576020L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCouponPaymentLog couponPaymentLog = new QCouponPaymentLog("couponPaymentLog");

    public final io.saim.dash.account.general.model.QGeneralUser member;

    public final io.saim.dash.account.partner.model.QPartnerUser partner;

    public final QCouponPaymentCode paymentCode;

    public final NumberPath<Long> paymentId = createNumber("paymentId", Long.class);

    public final EnumPath<io.saim.dash.coupon.common.constant.PaymentStatus> status = createEnum("status", io.saim.dash.coupon.common.constant.PaymentStatus.class);

    public final DateTimePath<java.time.LocalDateTime> usedAt = createDateTime("usedAt", java.time.LocalDateTime.class);

    public QCouponPaymentLog(String variable) {
        this(CouponPaymentLog.class, forVariable(variable), INITS);
    }

    public QCouponPaymentLog(Path<? extends CouponPaymentLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCouponPaymentLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCouponPaymentLog(PathMetadata metadata, PathInits inits) {
        this(CouponPaymentLog.class, metadata, inits);
    }

    public QCouponPaymentLog(Class<? extends CouponPaymentLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new io.saim.dash.account.general.model.QGeneralUser(forProperty("member"), inits.get("member")) : null;
        this.partner = inits.isInitialized("partner") ? new io.saim.dash.account.partner.model.QPartnerUser(forProperty("partner")) : null;
        this.paymentCode = inits.isInitialized("paymentCode") ? new QCouponPaymentCode(forProperty("paymentCode"), inits.get("paymentCode")) : null;
    }

}

