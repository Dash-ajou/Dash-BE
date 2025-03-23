package io.saim.dash.coupon.repository.Product;

import java.util.List;
import java.util.Optional;

import io.saim.dash.coupon.model.Product;

public interface ProductRepository {
	void save(Product product);

	Optional<Product> getById(Long id);

	List<Product> findAllById(List<Long> productIds);
}
