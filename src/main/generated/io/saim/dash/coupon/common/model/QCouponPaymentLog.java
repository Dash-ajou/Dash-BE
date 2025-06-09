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

    public final DateTimePath<java.time.LocalDateTime> canceledAt = createDateTime("canceledAt", java.time.LocalDateTime.class);

    public final StringPath capturedImage = createString("capturedImage");

    public final StringPath paidPaymentCode = createString("paidPaymentCode");

    public final io.saim.dash.account.partner.model.QPartnerUser partner;

    public final QCouponPaymentCode paymentCode;

    public final NumberPath<Long> paymentId = createNumber("paymentId", Long.class);

    public final EnumPath<io.saim.dash.coupon.common.constant.PaymentStatus> status = createEnum("status", io.saim.dash.coupon.common.constant.PaymentStatus.class);

    public final DateTimePath<java.time.LocalDateTime> usedAt = createDateTime("usedAt", java.time.LocalDateTime.class);

    public final io.saim.dash.account.general.model.QGeneralUser user;

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
        this.partner = inits.isInitialized("partner") ? new io.saim.dash.account.partner.model.QPartnerUser(forProperty("partner")) : null;
        this.paymentCode = inits.isInitialized("paymentCode") ? new QCouponPaymentCode(forProperty("paymentCode"), inits.get("paymentCode")) : null;
        this.user = inits.isInitialized("user") ? new io.saim.dash.account.general.model.QGeneralUser(forProperty("user"), inits.get("user")) : null;
    }

}

