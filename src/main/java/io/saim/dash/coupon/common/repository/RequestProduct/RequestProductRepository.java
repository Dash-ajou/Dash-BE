package io.saim.dash.coupon.common.repository.RequestProduct;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.model.mapping.RequestProduct;

@Repository
public interface RequestProductRepository {
	List<RequestProduct> findByFilter(BooleanBuilder filter, Long page, Long size);
}
