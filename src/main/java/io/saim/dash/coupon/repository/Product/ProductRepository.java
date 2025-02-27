package io.saim.dash.coupon.repository.Product;

import io.saim.dash.coupon.model.Product;

public interface ProductRepository {
	Long save(Product product);

	Product getById(Long id);
}
