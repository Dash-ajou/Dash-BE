package io.saim.dash.coupon.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.saim.dash.coupon.common.constant.IssueStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Issue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@ManyToOne(optional = false)
	private VendorGroup vendorGroup;


	@ManyToOne(optional = false)
	private DUMMY_PartnerUser partner;

	@Column(nullable = false)
	private IssueStatus status;

	@OneToMany @Column(nullable = false)
	private List<Product> products = new ArrayList<>();

	@Builder
	private Issue(VendorGroup vendorGroup, DUMMY_PartnerUser partner,
		IssueStatus status,
		List<Product> products
	) {
		this.vendorGroup = vendorGroup;
		this.partner = partner;
		this.status = status;
		this.products = products;
	}

	public boolean isRequestedPartner(DUMMY_ServiceUser partner) {
		if (!DUMMY_PartnerUser.isPartnerUser(partner))
			return false;

		return this.partner.equals(partner);
	}

}
