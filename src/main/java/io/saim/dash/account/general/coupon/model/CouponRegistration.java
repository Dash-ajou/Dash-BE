package io.saim.dash.account.general.coupon.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "coupon_registration_log")
public class CouponRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "registration_id")
	private Long registrationId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coupon_management_id", nullable = false)
	private Coupon coupon;

	@Column(name = "member_id", nullable = false)
	private Long memberId;  //쿠폰을 보유한 회원 ID

	@Column(name = "registered_date", nullable = false)
	private LocalDate registeredDate;
}
