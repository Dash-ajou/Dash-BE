/*package io.saim.dash.coupon.common.model;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long registerId;

	@OneToOne
	@JoinColumn(nullable = false, name = "registerId")
	private DUMMY_GeneralUser registeredUser;

	@ManyToOne
	@JoinColumn(nullable = false, name = "couponId")
	private Coupon coupon;

	@Column(nullable = false) @Setter
	private Boolean isValid;

	@Column(nullable = false)
	private LocalDateTime registeredAt;

}
 */
