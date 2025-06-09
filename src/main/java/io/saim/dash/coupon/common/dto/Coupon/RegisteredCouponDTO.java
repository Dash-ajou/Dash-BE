package io.saim.dash.coupon.common.dto.Coupon;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import io.saim.dash.coupon.common.dto.GeneralUserDTO;
import io.saim.dash.coupon.common.dto.PartnerDTO;
import io.saim.dash.coupon.common.dto.PartnerSpecDTO;
import io.saim.dash.coupon.common.dto.Product.ProductDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.CouponRegistration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisteredCouponDTO {
	private Long couponId;
	private Long issueId;

	private PartnerSpecDTO partner;

	private ProductDTO product;

	private String registerCode;

	private CouponStatus status;

	private GeneralUserDTO register; // 등록자

	private String paidQRImage;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime registered_at; // 등록일시

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime expired_at; // 만료일시

	public RegisteredCouponDTO(Issue issue, Coupon coupon) {
		this.couponId = coupon.getCouponId();
		this.partner = new PartnerSpecDTO(issue.getRequest().getPartner());
		this.status = (issue.getIssueActiveStatus() == IssueActiveStatus.DISABLE)
			? CouponStatus.DISABLED
			: coupon.getCouponStatus()
		;
		this.expired_at = coupon.getExpiredAt();
		this.registerCode = coupon.getRegistrationCode();
		this.product = new ProductDTO(coupon.getProduct());

		this.register = null;
		this.registered_at = null;
	}

	public RegisteredCouponDTO(Issue issue, Coupon coupon, CouponRegistration couponRegistration) {
		this.couponId = coupon.getCouponId();
		this.issueId = issue.getIssueId();
		this.partner = new PartnerSpecDTO(issue.getRequest().getPartner());
		this.status = (issue.getIssueActiveStatus() == IssueActiveStatus.DISABLE)
			? CouponStatus.DISABLED
			: coupon.getCouponStatus()
		;
		this.expired_at = coupon.getExpiredAt();
		this.registerCode = coupon.getRegistrationCode();
		this.product = new ProductDTO(coupon.getProduct());

		if (couponRegistration == null) {
			this.register = null;
			this.registered_at = null;
		} else {
			this.register = new GeneralUserDTO(couponRegistration.getRegisteredUser());
			this.registered_at = couponRegistration.getRegisteredAt();
		}
	}

	public RegisteredCouponDTO(Issue issue, Coupon coupon, CouponRegistration couponRegistration, String paidQRImage) {
		this(issue, coupon, couponRegistration);
		this.setPaidQRImage(paidQRImage);
	}
}
