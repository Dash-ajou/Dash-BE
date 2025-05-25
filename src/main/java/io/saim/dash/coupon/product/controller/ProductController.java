package io.saim.dash.coupon.product.controller;

import java.util.List;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.coupon.common.dto.Product.RequestProductDTO;
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
		@AuthenticationPrincipal ServiceUser user,
		@RequestParam(required = false) Long request_id,
		@RequestParam(required = false) Long partner_id,
		@RequestParam(required = false) String product_name,
		@RequestParam(required = false) Long page,
		@RequestParam(required = false) Long size
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
