package io.saim.dash.coupon.common.repository.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import io.saim.dash.coupon.common.model.Product;
import io.saim.dash.coupon.common.repository.jpa.ProductJpaRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {

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
}
