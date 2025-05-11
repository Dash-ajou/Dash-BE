package io.saim.dash.coupon.common.util;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import io.saim.dash.coupon.common.model.QIssue;
import io.saim.dash.coupon.common.model.QRequest;

public class ManageQueryHelper {
	public static BooleanBuilder createFilterBuilder(
		String vendorName,
		String presidentName, String businessName,
		Boolean isCompletionIncluded,
		QIssue issueLog, QRequest issueRequest
	) {
		BooleanBuilder builder = new BooleanBuilder();

		addVendorNameFilter(builder, issueRequest, vendorName, presidentName);
		addPresidentFilter(builder, issueRequest, businessName);
		addCompletionFilter(builder, issueLog, isCompletionIncluded);

		return builder;
	}

	private static void addCompletionFilter(
		BooleanBuilder builder,
		QIssue issueLog,
		Boolean isCompletionIncluded
	) {
		if (isCompletionIncluded.equals(true))
			builder.and(issueLog.couponActiveStatus.eq(IssueActiveStatus.ENABLED));
	}

	private static void addPresidentFilter(
		BooleanBuilder builder,
		QRequest issueRequest,
		String businessName
	) {
		if (businessName != null && !businessName.isEmpty()) {
			builder.and(issueRequest.partner.partnerName.eq(businessName));
		}
	}

	private static void addVendorNameFilter(
		BooleanBuilder builder,
		QRequest request,
		String vendorName, String presidentName
	) {
		if (vendorName != null && !vendorName.isEmpty()) {
			builder.and(request.vendor.name.eq(vendorName));
		}

		if (presidentName != null && !presidentName.isEmpty()) {
			builder.and(request.vendor.name.eq(presidentName));
		}
	}



}
