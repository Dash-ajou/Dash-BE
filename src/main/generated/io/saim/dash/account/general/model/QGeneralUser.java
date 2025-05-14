package io.saim.dash.account.general.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGeneralUser is a Querydsl query type for GeneralUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGeneralUser extends EntityPathBase<GeneralUser> {

    private static final long serialVersionUID = -1536202049L;

    public static final QGeneralUser generalUser = new QGeneralUser("generalUser");

    public final NumberPath<Long> departmentId = createNumber("departmentId", Long.class);

    public final StringPath generalEmail = createString("generalEmail");

    public final NumberPath<Long> generalId = createNumber("generalId", Long.class);

    public final StringPath generalName = createString("generalName");

    public final StringPath generalPhone = createString("generalPhone");

    public final StringPath generalType = createString("generalType");

    public final DateTimePath<java.time.LocalDateTime> joinedAt = createDateTime("joinedAt", java.time.LocalDateTime.class);

    public final StringPath password = createString("password");

    public final ListPath<Password, QPassword> passwords = this.<Password, QPassword>createList("passwords", Password.class, QPassword.class, PathInits.DIRECT2);

    public final NumberPath<Long> vendorGroupId = createNumber("vendorGroupId", Long.class);

    public QGeneralUser(String variable) {
        super(GeneralUser.class, forVariable(variable));
    }

    public QGeneralUser(Path<? extends GeneralUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGeneralUser(PathMetadata metadata) {
        super(GeneralUser.class, metadata);
    }

}

