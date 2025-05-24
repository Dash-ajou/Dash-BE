package io.saim.dash.coupon.common.repository.Product;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.saim.dash.coupon.common.model.Product;
import io.saim.dash.coupon.common.model.QProduct;
import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.repository.jpa.ProductJpaRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {

	private final JPAQueryFactory queryFactory;
	private final ProductJpaRepository productJpaRepository;

	@Override
	public void save(Product product) {
		productJpaRepository.save(product);
	}

	@Override
	public Product findById(Long id) {
		Optional<Product> product = productJpaRepository.findById(id);
		if (product.isEmpty())
			throw new ServiceException(ServiceExceptionContent.PRODUCT_NOT_FOUND);

		return product.get();
	}

	@Override
	public List<Product> findAllById(List<Long> productIds) {
		return productJpaRepository.findAllById(productIds);
	}

	@Override
	public Product getReferenceById(Long productId) {
		return productJpaRepository.getReferenceById(productId);
	}

	@Override
	public List<Product> findByFilter(BooleanBuilder filter, Long page, Long size) {
		QProduct product = QProduct.product;

		if (page <= 0) page = 1L;
		if (size <= 10) size = 10L;
		else if (size % 10 != 0) size = (size/10)*10;

		return queryFactory
			.selectFrom(product)
			.where(filter)
			.offset(page * size).limit(size)
			.fetch();
	}
}
