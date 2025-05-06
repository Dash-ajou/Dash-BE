package io.saim.dash.coupon.common.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.model.mapping.RequestProduct;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long requestId;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vendor_id", nullable = false)
	private Vendor vendor;

	@ManyToOne(optional = false)
	private DUMMY_PartnerUser partner;

	@Column(nullable = false)
	private IssueStatus status;

	@OneToMany(
		mappedBy = "request", fetch = FetchType.LAZY,
		cascade = CascadeType.ALL, orphanRemoval = true
	)
	private List<RequestProduct> requestProducts = new ArrayList<>();

	public void addRequestProduct(Product product, Integer quantity) {
		addRequestProduct(product, quantity, product.getPrice());
	}

	public void addRequestProduct(Product product, Integer quantity, Integer price) {
		RequestProduct requestProduct = new RequestProduct(product, this, quantity, price);
		this.requestProducts.add(requestProduct);
	}

	public boolean isRequestedPartner(DUMMY_ServiceUser partner) {
		if (!DUMMY_PartnerUser.isPartnerUser(partner))
			return false;

		return this.partner.equals(partner);
	}

}
