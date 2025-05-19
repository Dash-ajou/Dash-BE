
/*
package io.saim.dash.coupon.product.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.dto.Product.RequestProductDTO;
import io.saim.dash.coupon.common.model.Product;
import io.saim.dash.coupon.common.model.mapping.RequestProduct;
import io.saim.dash.coupon.common.repository.Product.ProductRepository;
import io.saim.dash.coupon.common.repository.RequestProduct.RequestProductRepository;
import io.saim.dash.coupon.common.util.ProductQueryHelper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final RequestProductRepository requestProductRepository;

	public List<RequestProductDTO> findProducts(
		Long requestId, Long partnerId,
		String productName,
		Long page, Long size
	) {
		List<Product> defaultProducts = findDefaultProducts(partnerId, productName, page, size);
		List<RequestProduct> requestProducts = findRequestProducts(requestId,
			partnerId, productName, page, size, defaultProducts);

		return applyRequestPrice(defaultProducts, requestProducts);
	}

	private List<RequestProductDTO> applyRequestPrice(List<Product> products, List<RequestProduct> requestProducts) {
		Map<Long, RequestProduct> mappedRequestProducts = requestProducts.stream()
			.collect(Collectors.toMap(
				v -> v.getProduct().getProductId(),
				v -> v
			));

		return products.stream()
			.map(v -> new RequestProductDTO(v, mappedRequestProducts.get(v.getProductId())))
			.toList();
	}

	private List<RequestProduct> findRequestProducts(
		Long requestId, Long partnerId, String productName,
		Long page, Long size,
		List<Product> products
	) {
		List<Long> productIds = products.stream().map(Product::getProductId).toList();
		BooleanBuilder requestProductFindFilterBuilder = ProductQueryHelper.createRequestProductFindFilterBuilder(
			productIds,
			requestId, partnerId, productName
		);

		return requestProductRepository.findByFilter(
			requestProductFindFilterBuilder,
			page, size
		);
	}

	private List<Product> findDefaultProducts(Long partnerId, String productName, Long page, Long size) {
		BooleanBuilder productFindFilterBuilder = ProductQueryHelper.createProductFindFilterBuilder(partnerId, productName);
		return productRepository.findByFilter(
			productFindFilterBuilder,
			page, size
		);
	}

}
*/
