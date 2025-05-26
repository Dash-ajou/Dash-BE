package io.saim.dash.coupon.product.controller;

import java.util.List;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.common.model.UserType;
import io.saim.dash.coupon.common.dto.Product.RequestProductDTO;
import io.saim.dash.coupon.product.dto.ProductFindResponseDTO;
import io.saim.dash.coupon.product.service.ProductService;
import io.saim.dash.global.dto.PagingResponse;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon/product")
public class ProductController {

	private final ProductService productService;

	@GetMapping("/list")
	public PagingResponse<ProductFindResponseDTO> findProducts(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestParam(required = false) Long request_id,
		@RequestParam(required = false) Long partner_id,
		@RequestParam(required = false) String product_name,
		@RequestParam(required = false, defaultValue = "1") Long page,
		@RequestParam(required = false, defaultValue = "10") Long size
	) {
		// ServiceUser user = getLoginUser(customUserDetails);

		List<RequestProductDTO> products = productService.findProducts(request_id, partner_id, product_name, page,
			size);
		List<ProductFindResponseDTO> responseProducts = products.stream()
			.map(ProductFindResponseDTO::new)
			.toList();

		return new PagingResponse<>(
			Math.toIntExact(page), Math.toIntExact(size),
			responseProducts
		);
	}

	private static ServiceUser getLoginUser(CustomUserDetails customUserDetails) {
		ServiceUser user;
		try {
			user = customUserDetails.getGeneralUser();
		} catch (Exception e) {
			user = customUserDetails.getPartnerUser();
		}
		user.setUserType(UserType.valueOf(customUserDetails.getUserType()));
		return user;
	}
}
