package io.saim.dash.coupon.common.dto.Coupon;

import java.util.List;

import io.saim.dash.coupon.common.constant.CodeType;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.dto.PartnerSpecDTO;
import io.saim.dash.coupon.common.dto.Product.RequestProductDTO;
import io.saim.dash.coupon.common.dto.VendorDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.model.mapping.RequestProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class CouponCodeInfo {
	private CodeType type;
	private VendorDTO vendor;
	private PartnerSpecDTO partner;
	private RequestProductDTO product;
	private CouponStatus status;

	public CouponCodeInfo(CodeType codeType, Coupon coupon) {
		this.type = codeType;

		Issue issue = coupon.getIssue();
		Request request = issue.getRequest();
		this.vendor = new VendorDTO(request.getVendor());
		this.partner = new PartnerSpecDTO(request.getPartner());
		this.product = new RequestProductDTO(coupon.getProduct(), coupon.getRequestProduct());
		this.status = coupon.getCouponStatus();
	}
}
