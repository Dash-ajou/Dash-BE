/*
package io.saim.dash.coupon.product.controller;

import java.util.List;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.saim.dash.coupon.common.dto.Product.RequestProductDTO;
import io.saim.dash.coupon.common.model.DUMMY_ServiceUser;
import io.saim.dash.coupon.product.dto.ProductFindResponseDTO;
import io.saim.dash.coupon.product.service.ProductService;
import io.saim.dash.global.dto.PagingResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupon/product")
public class ProductController {

	private final ProductService productService;

	@GetMapping("/list")
	public PagingResponse<ProductFindResponseDTO> findProducts(
		@AuthenticationPrincipal DUMMY_ServiceUser user,
		@PathVariable Long request_id,
		@PathVariable Long partner_id,
		@PathVariable String product_name,
		@PathVariable Long page,
		@PathVariable Long size
	) {

		List<RequestProductDTO> products = productService.findProducts(request_id, partner_id, product_name, page,
			size);
		List<ProductFindResponseDTO> responseProducts = products.stream()
			.map(ProductFindResponseDTO::new)
			.toList();

		return new PagingResponse<>(
			Integer.parseInt(String.valueOf(page)), Integer.parseInt(String.valueOf(size)),
			responseProducts
		);
	}
}


 */
