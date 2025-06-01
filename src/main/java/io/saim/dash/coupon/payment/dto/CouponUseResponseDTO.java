package io.saim.dash.coupon.payment.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.saim.dash.coupon.common.dto.VendorDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponPaymentLog;
import io.saim.dash.coupon.common.model.Request;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CouponUseResponseDTO {
	private final Boolean result;
	private final Long id;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private final LocalDateTime used_at;

	private final VendorDTO vendor;
	private final ProductBriefDTO product;

	public CouponUseResponseDTO(CouponPaymentLog couponPaymentLog) {
		this.result = true;
		this.id = couponPaymentLog.getPaymentId();
		this.used_at = couponPaymentLog.getUsedAt();

		Coupon coupon = couponPaymentLog.getPaymentCode().getCoupon();
		Request request = coupon.getIssue().getRequest();

		this.product = new ProductBriefDTO(coupon.getProduct());
		this.vendor = new VendorDTO(request.getVendor());
	}
}
