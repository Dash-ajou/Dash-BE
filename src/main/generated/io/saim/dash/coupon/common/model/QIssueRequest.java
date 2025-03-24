package io.saim.dash.coupon.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIssueRequest is a Querydsl query type for IssueRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIssueRequest extends EntityPathBase<IssueRequest> {

    private static final long serialVersionUID = 834780926L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIssueRequest issueRequest = new QIssueRequest("issueRequest");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final QDUMMY_PartnerUser partner;

    public final ListPath<Product, QProduct> products = this.<Product, QProduct>createList("products", Product.class, QProduct.class, PathInits.DIRECT2);

    public final NumberPath<Long> requestId = createNumber("requestId", Long.class);

    public final EnumPath<io.saim.dash.coupon.common.constant.IssueStatus> status = createEnum("status", io.saim.dash.coupon.common.constant.IssueStatus.class);

    public final QVendorGroup vendorGroup;

    public QIssueRequest(String variable) {
        this(IssueRequest.class, forVariable(variable), INITS);
    }

    public QIssueRequest(Path<? extends IssueRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIssueRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIssueRequest(PathMetadata metadata, PathInits inits) {
        this(IssueRequest.class, metadata, inits);
    }

    public QIssueRequest(Class<? extends IssueRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.partner = inits.isInitialized("partner") ? new QDUMMY_PartnerUser(forProperty("partner")) : null;
        this.vendorGroup = inits.isInitialized("vendorGroup") ? new QVendorGroup(forProperty("vendorGroup")) : null;
    }

}

