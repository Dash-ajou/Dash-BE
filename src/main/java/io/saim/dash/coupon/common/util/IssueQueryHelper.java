/*package io.saim.dash.coupon.common.util;

import java.time.LocalDateTime;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.model.QRequest;

public class IssueQueryHelper {
	public static BooleanBuilder createFilterBuilder(
		String createat_start, String createat_end,
		String business_name, String owner_phone, IssueStatus status,
		QRequest issueReq
	) {
		BooleanBuilder builder = new BooleanBuilder();

		addCreateAtFilter(builder, issueReq, createat_start, createat_end);
		addBusinesNameFilter(builder, issueReq, business_name);
		addBusinesOwnerPnFilter(builder, issueReq, owner_phone);
		addIssueStatuFilter(builder, issueReq, status);

		return builder;
	}

	private static void addCreateAtFilter(BooleanBuilder builder, QRequest issueRequest, String createatStart, String createatEnd) {
		if (createatStart != null) {
			LocalDateTime createAtStart = LocalDateTime.parse(createatStart);
			builder.and(issueRequest.createdAt.gt(createAtStart));
		}

		if (createatEnd != null) {
			LocalDateTime createAtEnd = LocalDateTime.parse(createatEnd);
			builder.and(issueRequest.createdAt.lt(createAtEnd));
		}
	}

	private static void addBusinesNameFilter(BooleanBuilder builder, QRequest issueRequest, String businessName) {
		if (businessName == null || businessName.isEmpty()) return;

		builder.and(issueRequest.partner.name.eq(businessName));
	}

	private static void addBusinesOwnerPnFilter(BooleanBuilder builder, QRequest issueRequest, String ownerPhone) {
		if (ownerPhone == null || ownerPhone.isEmpty()) return;

		builder.and(issueRequest.partner.phone.eq(ownerPhone));
	}

	private static void addIssueStatuFilter(BooleanBuilder builder, QRequest issueRequest, IssueStatus status) {
		if (status == null) return;

		builder.and(issueRequest.status.eq(status));
	}
}

 */
