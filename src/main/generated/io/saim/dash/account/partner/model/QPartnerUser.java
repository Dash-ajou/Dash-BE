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

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final BooleanPath isTemporary = createBoolean("isTemporary");

    public final StringPath ownerEmail = createString("ownerEmail");

    public final StringPath ownerName = createString("ownerName");

    public final StringPath ownerPhone = createString("ownerPhone");

    public final StringPath partnerAddress = createString("partnerAddress");

    public final NumberPath<Long> partnerId = createNumber("partnerId", Long.class);

    public final StringPath partnerName = createString("partnerName");

    public final StringPath password = createString("password");

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

