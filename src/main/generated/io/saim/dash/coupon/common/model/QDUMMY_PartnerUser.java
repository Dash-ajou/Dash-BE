package io.saim.dash.coupon.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;

/**
 * QDUMMY_PartnerUser is a Querydsl query type for DUMMY_PartnerUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDUMMY_PartnerUser extends EntityPathBase<DUMMY_PartnerUser> {

    private static final long serialVersionUID = 466137015L;

    public static final QDUMMY_PartnerUser dUMMY_PartnerUser = new QDUMMY_PartnerUser("dUMMY_PartnerUser");

    public final QDUMMY_ServiceUser _super = new QDUMMY_ServiceUser(this);

    //inherited
    public final StringPath email = _super.email;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> joinedAt = _super.joinedAt;

    //inherited
    public final StringPath name = _super.name;

    public final StringPath ownerName = createString("ownerName");

    public final StringPath partnerAddress = createString("partnerAddress");

    public final StringPath partnerName = createString("partnerName");

    //inherited
    public final StringPath phone = _super.phone;

    public final EnumPath<DUMMY_UserType> userType = createEnum("userType", DUMMY_UserType.class);

    public QDUMMY_PartnerUser(String variable) {
        super(DUMMY_PartnerUser.class, forVariable(variable));
    }

    public QDUMMY_PartnerUser(Path<? extends DUMMY_PartnerUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDUMMY_PartnerUser(PathMetadata metadata) {
        super(DUMMY_PartnerUser.class, metadata);
    }

}

