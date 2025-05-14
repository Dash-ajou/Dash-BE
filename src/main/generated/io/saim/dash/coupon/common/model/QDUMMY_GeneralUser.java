package io.saim.dash.coupon.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDUMMY_GeneralUser is a Querydsl query type for DUMMY_GeneralUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDUMMY_GeneralUser extends EntityPathBase<DUMMY_GeneralUser> {

    private static final long serialVersionUID = -134406892L;

    public static final QDUMMY_GeneralUser dUMMY_GeneralUser = new QDUMMY_GeneralUser("dUMMY_GeneralUser");

    public final QDUMMY_ServiceUser _super = new QDUMMY_ServiceUser(this);

    //inherited
    public final StringPath email = _super.email;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> joinedAt = _super.joinedAt;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final StringPath phone = _super.phone;

    public final ListPath<UserVendor, QUserVendor> vendors = this.<UserVendor, QUserVendor>createList("vendors", UserVendor.class, QUserVendor.class, PathInits.DIRECT2);

    public QDUMMY_GeneralUser(String variable) {
        super(DUMMY_GeneralUser.class, forVariable(variable));
    }

    public QDUMMY_GeneralUser(Path<? extends DUMMY_GeneralUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDUMMY_GeneralUser(PathMetadata metadata) {
        super(DUMMY_GeneralUser.class, metadata);
    }

}

