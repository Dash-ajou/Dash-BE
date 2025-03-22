package io.saim.dash.coupon.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIssueLog is a Querydsl query type for IssueLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIssueLog extends EntityPathBase<IssueLog> {

    private static final long serialVersionUID = 1812994320L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIssueLog issueLog = new QIssueLog("issueLog");

    public final EnumPath<io.saim.dash.coupon.common.constant.ActiveStatus> activeStatus = createEnum("activeStatus", io.saim.dash.coupon.common.constant.ActiveStatus.class);

    public final DateTimePath<java.time.LocalDateTime> confirmedAt = createDateTime("confirmedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QIssue issue;

    public final NumberPath<Long> issueCnt = createNumber("issueCnt", Long.class);

    public final DateTimePath<java.time.LocalDateTime> paidAt = createDateTime("paidAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> paidPrice = createNumber("paidPrice", Long.class);

    public QIssueLog(String variable) {
        this(IssueLog.class, forVariable(variable), INITS);
    }

    public QIssueLog(Path<? extends IssueLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIssueLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIssueLog(PathMetadata metadata, PathInits inits) {
        this(IssueLog.class, metadata, inits);
    }

    public QIssueLog(Class<? extends IssueLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.issue = inits.isInitialized("issue") ? new QIssue(forProperty("issue"), inits.get("issue")) : null;
    }

}

