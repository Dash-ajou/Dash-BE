package io.saim.dash.account.auth.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPhoneVerification is a Querydsl query type for PhoneVerification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPhoneVerification extends EntityPathBase<PhoneVerification> {

    private static final long serialVersionUID = 1215083655L;

    public static final QPhoneVerification phoneVerification = new QPhoneVerification("phoneVerification");

    public final DateTimePath<java.time.LocalDateTime> expiresIn = createDateTime("expiresIn", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath requestTime = createString("requestTime");

    public final StringPath userPhone = createString("userPhone");

    public final BooleanPath userVerified = createBoolean("userVerified");

    public final StringPath userVerifyCode = createString("userVerifyCode");

    public QPhoneVerification(String variable) {
        super(PhoneVerification.class, forVariable(variable));
    }

    public QPhoneVerification(Path<? extends PhoneVerification> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPhoneVerification(PathMetadata metadata) {
        super(PhoneVerification.class, metadata);
    }

}

