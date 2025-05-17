package io.saim.dash.account.general.coupon.model;

import io.saim.dash.account.general.model.GeneralUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "coupon_management")
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "coupon_management_id")
	private Long couponId;

	@Column(name = "coupon_number", nullable = false, unique = true)
	private String couponNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "general_user_id", nullable = false)
	private GeneralUser generalUser;

	@Enumerated(EnumType.STRING)
	@Column(name = "coupon_status", nullable = false)
	private CouponStatus couponStatus;

	@Column(name = "created_date", nullable = false)
	private LocalDate createdDate;

	@OneToMany(mappedBy = "coupon", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<CouponRegistration> registrations;
	public enum CouponStatus {
		ACTIVE, EXPIRED, USED
	}


}
