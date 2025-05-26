package io.saim.dash.account.general.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MyPageResponseDTO {
	private String status;
	private String message;
	private Data data;

	@Getter
	@Setter
	@AllArgsConstructor
	public static class Data {
		private String generalName;
		private CouponStatus couponStatus;
		private Menus menus;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class CouponStatus {
		@JsonProperty("usable_coupons")
		private int usableCoupons;

		@JsonProperty("used_coupons")
		private int usedCoupons;
	}

	@Getter
	@Setter
	public static class Menus {
		private MenuItem[] myInfo = {
			new MenuItem("계정 정보", "/general/account"),
			new MenuItem("비밀번호 변경하기", "/auth/password-reset/request")
		};

		private MenuItem[] customerCenter = {
			new MenuItem("공지사항", "/support/notice"),
			new MenuItem("FAQ", "/support/faq"),
			new MenuItem("문의하기", "/support/contact")
		};

		@Getter
		@Setter
		@AllArgsConstructor
		public static class MenuItem {
			private String title;
			private String url;
		}
	}
}
