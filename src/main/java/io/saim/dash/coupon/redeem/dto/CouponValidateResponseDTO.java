package io.saim.dash.coupon.redeem.dto;

import io.saim.dash.coupon.common.constant.CodeType;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.dto.Coupon.CouponPaymentBriefLogDTO;
import io.saim.dash.coupon.common.dto.PartnerDTO;
import io.saim.dash.coupon.common.dto.VendorDTO;

public class CouponValidateResponseDTO {
	private final CodeType type;
	private final VendorDTO vendor;
	private final PartnerDTO partner;
	private final ProductDTO product;
	private final CouponStatus stauts;
	private final CouponPaymentBriefLogDTO redeem;

	public CouponValidateResponseDTO() {
		this.type = CodeType.PAYMENT_CODE;
		this.stauts = CouponStatus.USED;
		this.partner = new PartnerDTO(
			"4t5fr3dew",
			"010-1234-1234"
		);
		this.vendor = new VendorDTO(
			"Bobby Gutkowski",
			"Assunta_Wehner35@yahoo.com",
			"(415) 529-8392"
		);
		this.product = new ProductDTO(
			87542090L,
			69952994L,
			"Bespoke Bronze Shirt",
			660L
		);
		this.redeem = new CouponPaymentBriefLogDTO(
			65432L,
			"IJOSE65432",
			"2025-01-07 03:46:19"
		);
	}
}
