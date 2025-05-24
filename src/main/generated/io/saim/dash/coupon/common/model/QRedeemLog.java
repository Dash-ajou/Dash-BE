package io.saim.dash.coupon.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRedeemLog is a Querydsl query type for RedeemLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRedeemLog extends EntityPathBase<RedeemLog> {

    private static final long serialVersionUID = -1733765760L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRedeemLog redeemLog = new QRedeemLog("redeemLog");

    public final io.saim.dash.account.general.model.QGeneralUser member;

    public final io.saim.dash.account.partner.model.QPartnerUser partner;

    public final QCouponPaymentCode payment;

    public final NumberPath<Long> redeemId = createNumber("redeemId", Long.class);

    public final EnumPath<io.saim.dash.coupon.common.constant.RedeemStatus> status = createEnum("status", io.saim.dash.coupon.common.constant.RedeemStatus.class);

    public final DateTimePath<java.time.LocalDateTime> usedAt = createDateTime("usedAt", java.time.LocalDateTime.class);

    public QRedeemLog(String variable) {
        this(RedeemLog.class, forVariable(variable), INITS);
    }

    public QRedeemLog(Path<? extends RedeemLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRedeemLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRedeemLog(PathMetadata metadata, PathInits inits) {
        this(RedeemLog.class, metadata, inits);
    }

    public QRedeemLog(Class<? extends RedeemLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new io.saim.dash.account.general.model.QGeneralUser(forProperty("member"), inits.get("member")) : null;
        this.partner = inits.isInitialized("partner") ? new io.saim.dash.account.partner.model.QPartnerUser(forProperty("partner")) : null;
        this.payment = inits.isInitialized("payment") ? new QCouponPaymentCode(forProperty("payment"), inits.get("payment")) : null;
    }

}

