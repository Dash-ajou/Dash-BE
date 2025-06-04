package io.saim.dash.coupon.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIssue is a Querydsl query type for Issue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIssue extends EntityPathBase<Issue> {

    private static final long serialVersionUID = -796705199L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIssue issue = new QIssue("issue");

    public final ListPath<Coupon, QCoupon> coupons = this.<Coupon, QCoupon>createList("coupons", Coupon.class, QCoupon.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> decidedAt = createDateTime("decidedAt", java.time.LocalDateTime.class);

    public final EnumPath<io.saim.dash.coupon.common.constant.IssueActiveStatus> issueActiveStatus = createEnum("issueActiveStatus", io.saim.dash.coupon.common.constant.IssueActiveStatus.class);

    public final NumberPath<Long> issueCnt = createNumber("issueCnt", Long.class);

    public final NumberPath<Long> issueId = createNumber("issueId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> paidAt = createDateTime("paidAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> paidPrice = createNumber("paidPrice", Long.class);

    public final io.saim.dash.account.partner.model.QPartnerUser partner;

    public final QRequest request;

    public final NumberPath<Long> usedCnt = createNumber("usedCnt", Long.class);

    public final QVendor vendor;

    public QIssue(String variable) {
        this(Issue.class, forVariable(variable), INITS);
    }

    public QIssue(Path<? extends Issue> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIssue(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIssue(PathMetadata metadata, PathInits inits) {
        this(Issue.class, metadata, inits);
    }

    public QIssue(Class<? extends Issue> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.partner = inits.isInitialized("partner") ? new io.saim.dash.account.partner.model.QPartnerUser(forProperty("partner")) : null;
        this.request = inits.isInitialized("request") ? new QRequest(forProperty("request"), inits.get("request")) : null;
        this.vendor = inits.isInitialized("vendor") ? new QVendor(forProperty("vendor")) : null;
    }

}

