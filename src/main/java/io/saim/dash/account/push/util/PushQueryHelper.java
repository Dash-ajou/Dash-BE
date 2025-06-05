package io.saim.dash.account.push.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.account.push.dto.UserSearchResultDTO;
import io.saim.dash.account.push.model.QPush;
import io.saim.dash.coupon.common.constant.IssueStatus;

public class PushQueryHelper {

	public static BooleanBuilder createFilterBuilder(
		UserSearchResultDTO receiver,
		Boolean isReaded, String receivedAtFrom, String receivedAtTo
	) {
		BooleanBuilder builder = new BooleanBuilder();

		addReceiverFilter(builder, receiver);
		addIsReadedFilter(builder, isReaded);
		addReceivedAtFilter(builder, receivedAtFrom, receivedAtTo);

		return builder;
	}

	private static void addIsReadedFilter(BooleanBuilder builder, Boolean isReaded) {
		QPush push = QPush.push;
		if (isReaded == null) return;
		if (isReaded) builder.and(push.readAt.isNotNull());
		else builder.and(push.readAt.isNull());
	}

	private static void addReceivedAtFilter(BooleanBuilder builder, String receivedAtFrom, String receivedAtTo) {
		QPush push = QPush.push;
		if (receivedAtFrom != null && !receivedAtFrom.isEmpty()) {
			LocalDateTime parsedReceivedAtStart = parseDateTime(receivedAtFrom);
			builder.and(push.receivedAt.gt(parsedReceivedAtStart));
		}

		if (receivedAtTo != null && !receivedAtTo.isEmpty()) {
			LocalDateTime receivedAtEnd = parseDateTime(receivedAtTo);
			builder.and(push.receivedAt.lt(receivedAtEnd));
		}
	}

	private static LocalDateTime parseDateTime(String stringDateTime) {
		try {
			return LocalDateTime.parse(stringDateTime);
		} catch (java.time.format.DateTimeParseException e) {
			LocalDate dateOnly = LocalDate.parse(stringDateTime);
			return dateOnly.atStartOfDay();
		}
	}

	private static void addReceiverFilter(BooleanBuilder builder, UserSearchResultDTO receiver) {
		QPush push = QPush.push;
		if (receiver.isPartner())
			builder.and(push.receiver_partner.eq(receiver.partnerUser()));
		else
			builder.and(push.receiver_general.eq(receiver.generalUser()));
	}


}
