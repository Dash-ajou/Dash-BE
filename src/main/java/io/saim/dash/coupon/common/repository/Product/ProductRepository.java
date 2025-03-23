package io.saim.dash.coupon.common.repository.Product;

import java.util.List;
import java.util.Optional;

import io.saim.dash.coupon.common.model.Product;

public interface ProductRepository {
	void save(Product product);

	Optional<Product> getById(Long id);

	List<Product> findAllById(List<Long> productIds);
}
