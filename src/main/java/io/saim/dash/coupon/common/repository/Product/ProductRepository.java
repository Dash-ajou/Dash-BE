package io.saim.dash.coupon.common.repository.Product;

import java.util.List;
import java.util.Optional;

import io.saim.dash.coupon.common.model.Product;

public interface ProductRepository {
	void save(Product product);

	Product findById(Long id);

	List<Product> findAllById(List<Long> productIds);

	Product getReferenceById(Long productId);
}
