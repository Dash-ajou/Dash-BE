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

    public final io.saim.dash.account.common.model.QServiceUser _super = new io.saim.dash.account.common.model.QServiceUser(this);

    public final NumberPath<Long> departmentId = createNumber("departmentId", Long.class);

    //inherited
    public final StringPath email = _super.email;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> joinedAt = createDateTime("joinedAt", java.time.LocalDateTime.class);

    //inherited
    public final StringPath name = _super.name;

    public final StringPath ownerEmail = createString("ownerEmail");

    public final StringPath ownerName = createString("ownerName");

    public final StringPath ownerPhone = createString("ownerPhone");

    public final StringPath password = createString("password");

    public final ListPath<Password, QPassword> passwords = this.<Password, QPassword>createList("passwords", Password.class, QPassword.class, PathInits.DIRECT2);

    //inherited
    public final StringPath phone = _super.phone;

    public final StringPath type = createString("type");

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

