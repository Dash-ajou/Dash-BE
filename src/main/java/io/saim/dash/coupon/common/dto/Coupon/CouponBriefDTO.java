package io.saim.dash.coupon.common.dto.Coupon;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.saim.dash.coupon.common.dto.PartnerSpecDTO;
import io.saim.dash.coupon.common.dto.Product.ProductDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.Issue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import io.saim.dash.coupon.common.constant.CouponStatus;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CouponBriefDTO {
	private long couponId; // 쿠폰ID
	private long issueId; // 쿠폰 발행ID
	private ProductDTO product; // 상품ID
	private PartnerSpecDTO partner;
	private String registerCode; // 쿠폰등록코드
	private CouponStatus status; // 쿠폰상태

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime expiredAt; // 쿠폰 만료일자

	public CouponBriefDTO(Coupon coupon) {
		Issue issue = coupon.getIssue();

		this.couponId = coupon.getCouponId();
		this.issueId = issue.getIssueId();
		this.product = new ProductDTO(coupon.getProduct());
		this.partner = new PartnerSpecDTO(issue.getRequest().getPartner());
		this.registerCode = coupon.getRegistrationCode();
		this.status = coupon.getCouponStatus();
		this.expiredAt = coupon.getExpiredAt();
	}
}
