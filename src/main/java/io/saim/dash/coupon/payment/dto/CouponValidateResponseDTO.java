package io.saim.dash.coupon.payment.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.saim.dash.coupon.common.constant.CodeType;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.dto.Coupon.CouponCodeInfo;
import io.saim.dash.coupon.common.dto.PartnerDTO;
import io.saim.dash.coupon.common.dto.Product.ProductDTO;
import io.saim.dash.coupon.common.dto.VendorDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CouponValidateResponseDTO {
	private CodeType type;
	private VendorDTO vendor;
	private PartnerDTO partner;
	private ProductDTO product;
	private CouponStatus status;

	public CouponValidateResponseDTO(CouponCodeInfo couponCodeInfo) {
		this.type = couponCodeInfo.codeType();

		Coupon coupon = couponCodeInfo.coupon();
		Issue issue = coupon.getIssue();
		Request request = issue.getRequest();

		this.vendor = new VendorDTO(request.getVendor());
		this.partner = new PartnerDTO(request.getPartner());
		this.product = new ProductDTO(coupon.getProduct());
		this.status = coupon.getCouponStatus();
	}
}
