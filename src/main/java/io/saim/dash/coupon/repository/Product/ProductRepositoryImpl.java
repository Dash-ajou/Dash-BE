package io.saim.dash.coupon.repository.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import io.saim.dash.coupon.model.Product;
import io.saim.dash.coupon.repository.Issue.IssueJpaRepository;
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
	public Optional<Product> getById(Long id) {
		return productJpaRepository.findById(id);
	}

	@Override
	public List<Product> findAllById(List<Long> productIds) {
		return productJpaRepository.findAllById(productIds);
	}
}
