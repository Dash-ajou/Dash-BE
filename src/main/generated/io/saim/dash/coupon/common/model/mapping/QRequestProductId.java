package io.saim.dash.coupon.common.model.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRequestProductId is a Querydsl query type for RequestProductId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRequestProductId extends BeanPath<RequestProductId> {

    private static final long serialVersionUID = -1765040829L;

    public static final QRequestProductId requestProductId = new QRequestProductId("requestProductId");

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Long> requestId = createNumber("requestId", Long.class);

    public QRequestProductId(String variable) {
        super(RequestProductId.class, forVariable(variable));
    }

    public QRequestProductId(Path<? extends RequestProductId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRequestProductId(PathMetadata metadata) {
        super(RequestProductId.class, metadata);
    }

}

