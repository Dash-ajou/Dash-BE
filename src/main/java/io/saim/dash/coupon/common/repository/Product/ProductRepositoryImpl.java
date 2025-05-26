package io.saim.dash.coupon.common.repository.Product;


import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
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
	private final JdbcTemplate jdbcTemplate;
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

		if (page == null || page <= 0) page = 1L;
		if (size == null || size <= 10) size = 10L;
		// else if (size % 10 != 0) size = (size/10)*10;

		return queryFactory
			.selectFrom(product)
			.where(filter)
			.offset((page-1) * size).limit(size)
			.fetch();
	}

	@Override
	public void saveAll(List<Product> newProducts) {
		String sql = "INSERT INTO product (product_name, price, partner_id)"+
			"VALUES (?, ?, ?)";

		jdbcTemplate.batchUpdate(sql,
			newProducts,
			newProducts.size(),
			(PreparedStatement ps, Product product) -> {
				ps.setString(1, product.getProductName());
				ps.setString(2, product.getPrice().toString());
				ps.setString(3, product.getPartner().getId().toString());
			});
	}
}
