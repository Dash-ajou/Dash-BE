package io.saim.dash.coupon.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberVendor is a Querydsl query type for MemberVendor
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberVendor extends EntityPathBase<MemberVendor> {

    private static final long serialVersionUID = 877655911L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberVendor memberVendor = new QMemberVendor("memberVendor");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QDUMMY_GeneralUser user;

    public final QVendorGroup vendorGroup;

    public QMemberVendor(String variable) {
        this(MemberVendor.class, forVariable(variable), INITS);
    }

    public QMemberVendor(Path<? extends MemberVendor> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberVendor(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberVendor(PathMetadata metadata, PathInits inits) {
        this(MemberVendor.class, metadata, inits);
    }

    public QMemberVendor(Class<? extends MemberVendor> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QDUMMY_GeneralUser(forProperty("user")) : null;
        this.vendorGroup = inits.isInitialized("vendorGroup") ? new QVendorGroup(forProperty("vendorGroup")) : null;
    }

}

