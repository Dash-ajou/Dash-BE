package io.saim.dash.coupon.issue.dto;

import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class IssueCreateRequestDTO {
	private VendorRequestDTO vendor;
	private PartnerRequestDTO partner;
	private List<Long> products = new ArrayList<>();

	@AllArgsConstructor
	public static class VendorRequestDTO {
		private String vendor_name;

		@Nullable
		private String president_name;

		@Nullable
		private String president_phone;

		public String getVendorName() {
			return vendor_name;
		}

		public String getPresidentName() {
			return president_name;
		}

		public String getPresidentPhone() {
			return president_phone;
		}
	}

	@AllArgsConstructor
	public static class PartnerRequestDTO {
		private String business_name;
		private String owner_phone;

		public String getBusinessName() {
			return business_name;
		}

		public String getOwnerPhone() {
			return owner_phone;
		}
	}
}
