package io.saim.dash.coupon.common.dto.Issue;

import java.time.LocalDateTime;
import java.util.List;

import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.dto.PartnerDTO;
import io.saim.dash.coupon.common.dto.VendorDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.Product;
import io.saim.dash.coupon.common.model.Vendor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueResultDTO {

	private Issue issue;
	private Request requets;

	@Builder
	public IssueResultDTO(Issue issue) {
		this.issue = issue;
		this.requets = issue.getRequest();
	}

	@Builder
	public IssueResultDTO(Request request) {
		this.requets = request;
	}
}
