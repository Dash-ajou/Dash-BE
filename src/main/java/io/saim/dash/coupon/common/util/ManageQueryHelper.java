package io.saim.dash.coupon.common.util;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.account.partner.model.QPartnerUser;
import io.saim.dash.coupon.common.constant.CouponActiveStatus;
import io.saim.dash.coupon.common.model.QIssueLog;
import io.saim.dash.coupon.common.model.QIssueRequest;
import io.saim.dash.coupon.common.model.QVendorGroup;
import jakarta.validation.constraints.NotBlank;

public class ManageQueryHelper {
	public static BooleanBuilder createFilterBuilder(
		String vendorName,
		String presidentName, String businessName,
		Boolean isCompletionIncluded,
		QIssueLog issueLog, QIssueRequest issueRequest
	) {
		BooleanBuilder builder = new BooleanBuilder();

		addVendorNameFilter(builder, issueRequest, vendorName, presidentName);
		addPresidentFilter(builder, issueRequest, businessName);
		addCompletionFilter(builder, issueLog, isCompletionIncluded);

		return builder;
	}

	private static void addCompletionFilter(
		BooleanBuilder builder,
		QIssueLog issueLog,
		Boolean isCompletionIncluded
	) {
		if (isCompletionIncluded.equals(true))
			builder.and(issueLog.couponActiveStatus.eq(CouponActiveStatus.ENABLED));
	}

	private static void addPresidentFilter(
		BooleanBuilder builder,
		QIssueRequest issueRequest,
		String businessName
	) {
		if (businessName != null && !businessName.isEmpty()) {
			builder.and(issueRequest.partner.partnerName.eq(businessName));
		}
	}

	private static void addVendorNameFilter(
		BooleanBuilder builder,
		QIssueRequest issueRequest,
		String vendorName, String presidentName
	) {
		if (vendorName != null && !vendorName.isEmpty()) {
			builder.and(issueRequest.vendorGroup.name.eq(vendorName));
		}

		if (presidentName != null && !presidentName.isEmpty()) {
			builder.and(issueRequest.vendorGroup.name.eq(presidentName));
		}
	}



}
