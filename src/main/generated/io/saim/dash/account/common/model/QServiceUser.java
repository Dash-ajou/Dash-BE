package io.saim.dash.account.common.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QServiceUser is a Querydsl query type for ServiceUser
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QServiceUser extends EntityPathBase<ServiceUser> {

    private static final long serialVersionUID = -1058576447L;

    public static final QServiceUser serviceUser = new QServiceUser("serviceUser");

    public final StringPath email = createString("email");

    public final DateTimePath<java.time.LocalDateTime> joinedAt = createDateTime("joinedAt", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public QServiceUser(String variable) {
        super(ServiceUser.class, forVariable(variable));
    }

    public QServiceUser(Path<? extends ServiceUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QServiceUser(PathMetadata metadata) {
        super(ServiceUser.class, metadata);
    }

}

