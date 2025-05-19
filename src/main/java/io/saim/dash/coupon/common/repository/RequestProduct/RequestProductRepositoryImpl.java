/*
package io.saim.dash.coupon.common.repository.RequestProduct;

import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.saim.dash.coupon.common.model.mapping.QRequestProduct;
import io.saim.dash.coupon.common.model.mapping.RequestProduct;
import io.saim.dash.coupon.common.repository.jpa.RequestProductJpaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestProductRepositoryImpl implements RequestProductRepository {

	private final JPAQueryFactory queryFactory;
	private final RequestProductJpaRepository jpaRepository;

	@Override
	public List<RequestProduct> findByFilter(BooleanBuilder filter, Long page, Long size) {
		QRequestProduct requestProduct = QRequestProduct.requestProduct;

		if (page <= 0) page = 1L;
		if (size <= 10) size = 10L;
		else if (size % 10 != 0) size = (size/10)*10;

		return queryFactory
			.selectFrom(requestProduct)
			.where(filter)
			.offset(page * size).limit(size)
			.fetch();
	}
}

 */
