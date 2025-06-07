package io.saim.dash.account.partner.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPartnerPassword is a Querydsl query type for PartnerPassword
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPartnerPassword extends EntityPathBase<PartnerPassword> {

    private static final long serialVersionUID = 563953487L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPartnerPassword partnerPassword = new QPartnerPassword("partnerPassword");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath hashedPassword = createString("hashedPassword");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPartnerUser user;

    public QPartnerPassword(String variable) {
        this(PartnerPassword.class, forVariable(variable), INITS);
    }

    public QPartnerPassword(Path<? extends PartnerPassword> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPartnerPassword(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPartnerPassword(PathMetadata metadata, PathInits inits) {
        this(PartnerPassword.class, metadata, inits);
    }

    public QPartnerPassword(Class<? extends PartnerPassword> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QPartnerUser(forProperty("user")) : null;
    }

}

