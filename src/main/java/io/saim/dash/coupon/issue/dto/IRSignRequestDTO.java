package io.saim.dash.coupon.issue.dto;

import java.util.List;

import io.saim.dash.coupon.common.constant.IssueStatus;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public class IRSignRequestDTO {
	private final IssueStatus status;

	@Nullable
	private final IssuePaymentInfo payment;

	@RequiredArgsConstructor @Getter
	public static class IssuePaymentInfo {
		private final String paid_at;
		private final List<IRSignRequestDTO.IssuePaymentPriceInfo> prices;
		private final Long discount;

		public String getPaidAt() {
			return paid_at;
		}
	}

	@RequiredArgsConstructor @Getter
	public static class IssuePaymentPriceInfo {
		private final Long product_id;
		private final Long price;
	}
}

