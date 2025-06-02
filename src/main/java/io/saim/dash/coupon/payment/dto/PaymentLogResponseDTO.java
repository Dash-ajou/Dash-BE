package io.saim.dash.coupon.payment.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.constant.PaymentStatus;
import io.saim.dash.coupon.common.dto.GeneralUserDTO;
import io.saim.dash.coupon.common.dto.Product.RequestProductDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponPaymentLog;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentLogResponseDTO {
	private Long payment_id;
	private String payment_code;
	private GeneralUserDTO register;
	private RequestProductDTO product;
	private String scan_img;
	private PaymentStatus status;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime used_at;

	public PaymentLogResponseDTO(CouponPaymentLog couponPaymentLog) {
		this.payment_id = couponPaymentLog.getPaymentId();
		this.payment_code = couponPaymentLog.getPaymentCode().getPaymentCode();
		this.register = new GeneralUserDTO(couponPaymentLog.getMember());
		this.status = couponPaymentLog.getStatus();

		Coupon coupon = couponPaymentLog.getPaymentCode().getCoupon();
		this.product = new RequestProductDTO(
			coupon.getProduct(),
			coupon.getRequestProduct()
		);
	}
}
