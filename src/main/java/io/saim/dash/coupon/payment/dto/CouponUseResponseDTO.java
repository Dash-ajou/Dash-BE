package io.saim.dash.coupon.payment.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.saim.dash.coupon.common.dto.VendorDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponPaymentLog;
import io.saim.dash.coupon.common.model.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CouponUseResponseDTO {
	private Boolean result;
	private Long id;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime usedAt;

	private VendorDTO vendor;
	private ProductBriefDTO product;

	public CouponUseResponseDTO(CouponPaymentLog couponPaymentLog) {
		this.result = true;
		this.id = couponPaymentLog.getPaymentId();
		this.usedAt = couponPaymentLog.getUsedAt();

		Coupon coupon = couponPaymentLog.getPaymentCode().getCoupon();
		Request request = coupon.getIssue().getRequest();

		this.product = new ProductBriefDTO(coupon.getProduct());
		this.vendor = new VendorDTO(request.getVendor());
	}
}
