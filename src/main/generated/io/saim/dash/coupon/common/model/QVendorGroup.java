package io.saim.dash.coupon.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;

/**
 * QVendorGroup is a Querydsl query type for VendorGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVendorGroup extends EntityPathBase<VendorGroup> {

    private static final long serialVersionUID = 491570834L;

    public static final QVendorGroup vendorGroup = new QVendorGroup("vendorGroup");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Issue, QIssue> issues = this.<Issue, QIssue>createList("issues", Issue.class, QIssue.class, PathInits.DIRECT2);

    public final ListPath<MemberVendor, QMemberVendor> members = this.<MemberVendor, QMemberVendor>createList("members", MemberVendor.class, QMemberVendor.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath presidentName = createString("presidentName");

    public final StringPath presidentPhone = createString("presidentPhone");

    public QVendorGroup(String variable) {
        super(VendorGroup.class, forVariable(variable));
    }

    public QVendorGroup(Path<? extends VendorGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVendorGroup(PathMetadata metadata) {
        super(VendorGroup.class, metadata);
    }

}

