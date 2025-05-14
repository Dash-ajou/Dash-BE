package io.saim.dash.coupon.common.model.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRequestProduct is a Querydsl query type for RequestProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRequestProduct extends EntityPathBase<RequestProduct> {

    private static final long serialVersionUID = -1624181240L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRequestProduct requestProduct = new QRequestProduct("requestProduct");

    public final DatePath<java.time.LocalDate> created = createDate("created", java.time.LocalDate.class);

    public final QRequestProductId id;

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final io.saim.dash.coupon.common.model.QProduct product;

    public final NumberPath<Long> quantity = createNumber("quantity", Long.class);

    public final io.saim.dash.coupon.common.model.QRequest request;

    public QRequestProduct(String variable) {
        this(RequestProduct.class, forVariable(variable), INITS);
    }

    public QRequestProduct(Path<? extends RequestProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRequestProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRequestProduct(PathMetadata metadata, PathInits inits) {
        this(RequestProduct.class, metadata, inits);
    }

    public QRequestProduct(Class<? extends RequestProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QRequestProductId(forProperty("id")) : null;
        this.product = inits.isInitialized("product") ? new io.saim.dash.coupon.common.model.QProduct(forProperty("product"), inits.get("product")) : null;
        this.request = inits.isInitialized("request") ? new io.saim.dash.coupon.common.model.QRequest(forProperty("request"), inits.get("request")) : null;
    }

}

