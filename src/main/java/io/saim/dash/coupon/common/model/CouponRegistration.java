package io.saim.dash.coupon.common.model;


import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import io.saim.dash.account.general.model.GeneralUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "registration_id")
	private Long registrationId;

	@Setter @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "register_id")
	private GeneralUser registeredUser;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "coupon_id")
	private Coupon coupon;

	@Column(nullable = false) @Setter
	private Boolean isValid;

	@Column(name = "registered_at", nullable = false)
	@CreatedDate
	private LocalDateTime registeredAt = LocalDateTime.now();

	@Builder
	public CouponRegistration(Coupon coupon, GeneralUser registeredUser) {
		this.coupon = coupon;
		this.registeredUser = registeredUser;
		this.isValid = true;
	}

}
