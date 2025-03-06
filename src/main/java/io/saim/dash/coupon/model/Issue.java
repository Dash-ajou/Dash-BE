package io.saim.dash.coupon.model;

import java.util.List;

import io.saim.dash.account.general.model.SignupName;
import io.saim.dash.coupon.common.constant.IssueStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Issue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String created_at;

	@ManyToOne(optional = false)
	private SignupName vendor;

	@ManyToOne(optional = false)
	private DUMMY_PartnerUser partner;

	@Column(nullable = false)
	private IssueStatus status;

	@OneToMany
	@Column(nullable = false)
	private List<Product> products;

}
