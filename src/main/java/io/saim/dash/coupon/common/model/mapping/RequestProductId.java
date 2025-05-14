package io.saim.dash.coupon.common.model.mapping;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class RequestProductId implements Serializable {
	@Column(name = "product_id")
	private Long productId;

	@Column(name = "request_id")
	private Long requestId;
}
