package io.saim.dash.coupon.common.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CurrentTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.dto.Product.RequestProductDTO;
import io.saim.dash.coupon.common.model.mapping.RequestProduct;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import jakarta.persistence.CascadeType;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long couponId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "issue_id")
	private Issue issue;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(nullable = false)
	private String registrationCode;

	@Column(nullable = false) @Setter
	private CouponStatus couponStatus = CouponStatus.REGISTERABLE;

	@Column(nullable = false)
	private Long price;

	@CurrentTimestamp
	private LocalDateTime createdAt = LocalDateTime.now();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "general_user_id")
	private GeneralUser generalUser;

	// temp: 모든 발행쿠폰 유효기간 1개월로 설정
	@Column(nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime expiredAt = LocalDateTime.now().plusMonths(1);

	@Builder
	public Coupon(
		Issue issue,
		Product product,
		String registrationCode, CouponStatus couponStatus,
		Long price, LocalDateTime expiredAt
	) {
		this.issue = issue;
		this.product = product;
		this.registrationCode = registrationCode;
		this.couponStatus = couponStatus;
		this.price = price;
		this.expiredAt = expiredAt;
	}

	public RequestProduct getRequestProduct() {
		return this.issue.getRequest().getRequestProducts().stream()
			.filter(v -> v.getProduct().equals(this.getProduct()))
			.findFirst()
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.INVALID_COUPON));
	}
}
