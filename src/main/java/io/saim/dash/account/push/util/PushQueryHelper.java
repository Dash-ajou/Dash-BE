package io.saim.dash.account.push.util;

import java.time.LocalDateTime;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.account.push.dto.UserSearchResultDTO;
import io.saim.dash.account.push.model.QPush;
import io.saim.dash.coupon.common.constant.IssueStatus;

public class PushQueryHelper {

	public static BooleanBuilder createFilterBuilder(
		UserSearchResultDTO receiver,
		String receivedAtFrom, String receivedAtTo
	) {
		BooleanBuilder builder = new BooleanBuilder();

		addReceiverFilter(builder, receiver);
		addReceivedAtFilter(builder, receivedAtFrom, receivedAtTo);

		return builder;
	}

	private static void addReceivedAtFilter(BooleanBuilder builder, String receivedAtFrom, String receivedAtTo) {
		QPush push = QPush.push;
		if (receivedAtFrom != null) {
			LocalDateTime recievedAtStart = LocalDateTime.parse(receivedAtFrom);
			builder.and(push.receivedAt.gt(recievedAtStart));
		}

		if (receivedAtTo != null) {
			LocalDateTime receivedAtEnd = LocalDateTime.parse(receivedAtTo);
			builder.and(push.receivedAt.lt(receivedAtEnd));
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
