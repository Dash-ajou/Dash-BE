package io.saim.dash.account.push.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPush is a Querydsl query type for Push
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPush extends EntityPathBase<Push> {

    private static final long serialVersionUID = 1305620426L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPush push = new QPush("push");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath message = createString("message");

    public final DateTimePath<java.time.LocalDateTime> readAt = createDateTime("readAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> receivedAt = createDateTime("receivedAt", java.time.LocalDateTime.class);

    public final io.saim.dash.account.general.model.QGeneralUser receiver_general;

    public final io.saim.dash.account.partner.model.QPartnerUser receiver_partner;

    public final EnumPath<PushSenderType> senderType = createEnum("senderType", PushSenderType.class);

    public final EnumPath<PushTag> tag = createEnum("tag", PushTag.class);

    public final EnumPath<PushType> type = createEnum("type", PushType.class);

    public QPush(String variable) {
        this(Push.class, forVariable(variable), INITS);
    }

    public QPush(Path<? extends Push> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPush(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPush(PathMetadata metadata, PathInits inits) {
        this(Push.class, metadata, inits);
    }

    public QPush(Class<? extends Push> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiver_general = inits.isInitialized("receiver_general") ? new io.saim.dash.account.general.model.QGeneralUser(forProperty("receiver_general"), inits.get("receiver_general")) : null;
        this.receiver_partner = inits.isInitialized("receiver_partner") ? new io.saim.dash.account.partner.model.QPartnerUser(forProperty("receiver_partner")) : null;
    }

}

