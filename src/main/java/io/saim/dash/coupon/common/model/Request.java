package io.saim.dash.coupon.common.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.partner.model.PartnerUser;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long requestId;

	@Column(nullable = false)
	@CreatedDate @Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vendor_id", nullable = false)
	private Vendor vendor;

	@ManyToOne(optional = false)
	@JoinColumn(name = "partner_partner_id", nullable = false)
	private PartnerUser partner;

	@Column(nullable = false)
	@Builder.Default
	private IssueStatus status = IssueStatus.REQUESTED;

	@OneToMany(
		mappedBy = "request", fetch = FetchType.LAZY,
		cascade = CascadeType.ALL, orphanRemoval = true
	)
	@Builder.Default
	private List<RequestProduct> requestProducts = new ArrayList<>();

	public void addRequestProduct(Product product, Long quantity) {
		addRequestProduct(product, quantity, product.getPrice());
	}

	public void addRequestProduct(Product product, Long quantity, Long price) {
		RequestProduct requestProduct = new RequestProduct(product, this, quantity, price);
		this.requestProducts.add(requestProduct);
	}

	public boolean isRequestedPartner(ServiceUser partner) {
		if (!PartnerUser.isPartnerUser(partner))
			return false;

		return this.partner.equals(partner);
	}

}
