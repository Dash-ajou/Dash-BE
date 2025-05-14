package io.saim.dash.coupon.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDUMMY_ServiceUser is a Querydsl query type for DUMMY_ServiceUser
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QDUMMY_ServiceUser extends EntityPathBase<DUMMY_ServiceUser> {

    private static final long serialVersionUID = 1353329761L;

    public static final QDUMMY_ServiceUser dUMMY_ServiceUser = new QDUMMY_ServiceUser("dUMMY_ServiceUser");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> joinedAt = createDateTime("joinedAt", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public QDUMMY_ServiceUser(String variable) {
        super(DUMMY_ServiceUser.class, forVariable(variable));
    }

    public QDUMMY_ServiceUser(Path<? extends DUMMY_ServiceUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDUMMY_ServiceUser(PathMetadata metadata) {
        super(DUMMY_ServiceUser.class, metadata);
    }

}

