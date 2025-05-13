package io.saim.dash.coupon.common.util;

import java.util.List;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.model.QProduct;
import io.saim.dash.coupon.common.model.mapping.QRequestProduct;

public class ProductQueryHelper {
	public static BooleanBuilder createRequestProductFindFilterBuilder(
		List<Long> productIds,
		Long requestId,
		Long partnerId,
		String productName
	) {
		BooleanBuilder builder = new BooleanBuilder();

		addProductIdFilterToRequestProduct(builder, productIds);
		addRequestIdFilterToRequestProduct(builder, requestId);
		addPartnerIdFilterToRequestProduct(builder, partnerId);
		addProductNameFilterToRequestProduct(builder, productName);

		return builder;
	}

	public static BooleanBuilder createProductFindFilterBuilder(
		Long partnerId,
		String productName
	) {
		BooleanBuilder builder = new BooleanBuilder();

		addProductNameFilterToProduct(builder, productName);
		addPartnerIdFilterToProduct(builder, partnerId);

		return builder;
	}

	private static void addProductIdFilterToRequestProduct(BooleanBuilder builder, List<Long> productIds) {
		QRequestProduct requestProduct = QRequestProduct.requestProduct;
		builder.and(requestProduct.product.productId.in(productIds));
	}

	private static void addProductNameFilterToRequestProduct(BooleanBuilder builder, String productName) {
		if (productName == null) return;

		QRequestProduct product = QRequestProduct.requestProduct;
		builder.and(product.product.productName.eq(productName));
	}

	private static void addPartnerIdFilterToRequestProduct(BooleanBuilder builder, Long partnerId) {
		if (partnerId == null) return;

		QRequestProduct product = QRequestProduct.requestProduct;
		builder.and(product.product.partner.id.eq(partnerId));
	}

	private static void addRequestIdFilterToRequestProduct(BooleanBuilder builder, Long requestId) {
		if (requestId == null) return;

		QRequestProduct requestProduct = QRequestProduct.requestProduct;
		builder.and(requestProduct.request.requestId.eq(requestId));
	}


	private static void addProductNameFilterToProduct(BooleanBuilder builder, String productName) {
		if (productName == null) return;

		QProduct product = QProduct.product;
		builder.and(product.productName.eq(productName));
	}

	private static void addPartnerIdFilterToProduct(BooleanBuilder builder, Long partnerId) {
		if (partnerId == null) return;

		QProduct product = QProduct.product;
		builder.and(product.partner.id.eq(partnerId));
	}
}
