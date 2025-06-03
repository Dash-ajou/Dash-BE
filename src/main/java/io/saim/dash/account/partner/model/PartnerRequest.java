package io.saim.dash.account.partner.model;

import io.saim.dash.coupon.common.constant.IssueStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
@Table(name = "partner_request")
public class PartnerRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String partnerName;

	@Enumerated(EnumType.STRING)
	private IssueStatus requestStatus;
}
