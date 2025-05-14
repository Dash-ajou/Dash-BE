package io.saim.dash.coupon.redeem.dto;

import io.saim.dash.coupon.common.dto.Product.RequestProductDTO;
import io.saim.dash.coupon.common.dto.VendorDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ProductDTO {
	private final Long product_id;
	private final Long partner_id;
	private final String product_name;
	private final Long price;

	public ProductDTO(RequestProductDTO requestProductDTO) {
		this.product_id = requestProductDTO.getProductId();
		this.partner_id = requestProductDTO.getPartnerId();
		this.product_name = requestProductDTO.getProductName();
		this.price = requestProductDTO.getPrice();
	}
}

@RequiredArgsConstructor
public class CouponUseResponseDTO {
	private final Boolean result;
	private final Long id;
	private final String used_at;
	private final VendorDTO vendor;
	private final ProductDTO product;

	// 	DUMMY
	public CouponUseResponseDTO() {
		this.result = true;
		this.id = 213456L;
		this.used_at = "2025-01-07 03:46:19";
		this.vendor = new VendorDTO(
			"Bobby Gutkowski",
			"Assunta_Wehner35@yahoo.com",
			"(415) 529-8392"
		);
		this.product = new ProductDTO(
			87542090L,
			69952994L,
			"Bespoke Bronze Shirt",
			660L
		);
	}
}

