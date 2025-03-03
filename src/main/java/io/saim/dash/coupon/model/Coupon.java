package io.saim.dash.coupon.model;

import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long issueId;

	@Column(nullable = false)
	private Long productId;

	@Column(nullable = false)
	private String registerCode;

	@Column(nullable = false)
	private CouponStatus couponStatus;

}
