package io.saim.dash.coupon.common.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter
public class CouponPayment {

	@Id
	private String paymentCode;

	@ManyToOne
	@JoinColumn(nullable = false, name = "couponId")
	private Coupon coupon;

	@CreatedDate
	private LocalDateTime generatedAt;

	@Column(nullable = false)
	private LocalDateTime expiredAt;
}
