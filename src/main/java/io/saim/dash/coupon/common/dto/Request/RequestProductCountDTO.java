package io.saim.dash.coupon.common.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RequestProductCountDTO {

	private Long product_id;
	private Long count;

	private String product_name;
	private Boolean is_new;

	public RequestProductCountDTO(Long product_id, Long count) {
		this.product_id = product_id;
		this.count = count;
	}

	public Long getProductId() {
		return this.product_id;
	}

	public String getProductName() {
		return this.product_name;
	}

	public boolean isNewProduct() {
		return Boolean.TRUE.equals(is_new);
	}
}
