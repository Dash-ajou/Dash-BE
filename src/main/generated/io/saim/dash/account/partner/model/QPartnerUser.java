package io.saim.dash.account.partner.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPartnerUser is a Querydsl query type for PartnerUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPartnerUser extends EntityPathBase<PartnerUser> {

    private static final long serialVersionUID = -1466897793L;

    public static final QPartnerUser partnerUser = new QPartnerUser("partnerUser");

    public final io.saim.dash.account.common.model.QServiceUser _super = new io.saim.dash.account.common.model.QServiceUser(this);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isTemporary = createBoolean("isTemporary");

    public final DateTimePath<java.time.LocalDateTime> joinedAt = createDateTime("joinedAt", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final StringPath partnerAddress = createString("partnerAddress");

    public final StringPath partnerName = createString("partnerName");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final DateTimePath<java.time.LocalDateTime> temporaryRegisterDate = createDateTime("temporaryRegisterDate", java.time.LocalDateTime.class);

    public QPartnerUser(String variable) {
        super(PartnerUser.class, forVariable(variable));
    }

    public QPartnerUser(Path<? extends PartnerUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPartnerUser(PathMetadata metadata) {
        super(PartnerUser.class, metadata);
    }

}

