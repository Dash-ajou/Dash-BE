package io.saim.dash.coupon.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserVendor is a Querydsl query type for UserVendor
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserVendor extends EntityPathBase<UserVendor> {

    private static final long serialVersionUID = 1923370619L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserVendor userVendor = new QUserVendor("userVendor");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final io.saim.dash.account.general.model.QGeneralUser user;

    public final QVendor vendor;

    public QUserVendor(String variable) {
        this(UserVendor.class, forVariable(variable), INITS);
    }

    public QUserVendor(Path<? extends UserVendor> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserVendor(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserVendor(PathMetadata metadata, PathInits inits) {
        this(UserVendor.class, metadata, inits);
    }

    public QUserVendor(Class<? extends UserVendor> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new io.saim.dash.account.general.model.QGeneralUser(forProperty("user"), inits.get("user")) : null;
        this.vendor = inits.isInitialized("vendor") ? new QVendor(forProperty("vendor")) : null;
    }

}

