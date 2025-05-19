/*
package io.saim.dash.coupon.common.model.mapping;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.model.Product;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "request_product")
@Entity @Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RequestProduct implements Persistable<RequestProductId> {

	@EmbeddedId
	private RequestProductId id;

	@MapsId("productId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@MapsId("requestId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "request_id")
	private Request request;

	private Long quantity;
	private Long price;

	@CreatedDate
	private LocalDate created;

	public RequestProduct(
		Product product, Request request,
		Long quantity, Long price
	) {
		this.id = new RequestProductId(product.getProductId(), request.getRequestId());

		this.product = product;
		this.request = request;
		this.quantity = quantity;
		this.price = price;
	}

	@Override
	public RequestProductId getId() {
		return id;
	}

	@Override
	public boolean isNew() {
		return created == null;
	}
}


 */
