package io.saim.dash.coupon.common.util;

import java.time.LocalDateTime;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.model.QIssue;

public class IssueQueryHelper {
	public static BooleanBuilder createFilterBuilder(
		String createat_start, String createat_end,
		String business_name, String owner_phone, IssueStatus status,
		QIssue issue
	) {
		BooleanBuilder builder = new BooleanBuilder();

		addCreateAtFilter(builder, issue, createat_start, createat_end);
		addBusinesNameFilter(builder, issue, business_name);
		addBusinesOwnerPnFilter(builder, issue, owner_phone);
		addIssueStatuFilter(builder, issue, status);

		return builder;
	}

	private static void addCreateAtFilter(BooleanBuilder builder, QIssue issue, String createatStart, String createatEnd) {
		if (createatStart == null || createatEnd == null) return;

		LocalDateTime createAtStart = LocalDateTime.parse(createatStart);
		LocalDateTime createAtEnd = LocalDateTime.parse(createatEnd);

		builder.and(issue.createdAt.between(createAtStart, createAtEnd));
	}

	private static void addBusinesNameFilter(BooleanBuilder builder, QIssue issue, String businessName) {
		if (businessName == null || businessName.isEmpty()) return;

		builder.and(issue.partner.name.eq(businessName));
	}

	private static void addBusinesOwnerPnFilter(BooleanBuilder builder, QIssue issue, String ownerPhone) {
		if (ownerPhone == null || ownerPhone.isEmpty()) return;

		builder.and(issue.partner.phone.eq(ownerPhone));
	}

	private static void addIssueStatuFilter(BooleanBuilder builder, QIssue issue, IssueStatus status) {
		if (status == null) return;

		builder.and(issue.status.eq(status));
	}
}
