package io.saim.dash.account.partner.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPartnerRequest is a Querydsl query type for PartnerRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPartnerRequest extends EntityPathBase<PartnerRequest> {

    private static final long serialVersionUID = -2111961253L;

    public static final QPartnerRequest partnerRequest = new QPartnerRequest("partnerRequest");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath partnerName = createString("partnerName");

    public final EnumPath<io.saim.dash.coupon.common.constant.IssueStatus> requestStatus = createEnum("requestStatus", io.saim.dash.coupon.common.constant.IssueStatus.class);

    public QPartnerRequest(String variable) {
        super(PartnerRequest.class, forVariable(variable));
    }

    public QPartnerRequest(Path<? extends PartnerRequest> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPartnerRequest(PathMetadata metadata) {
        super(PartnerRequest.class, metadata);
    }

}

