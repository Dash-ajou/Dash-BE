package io.saim.dash.coupon.common.dto;

import io.saim.dash.coupon.common.model.Vendor;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class VendorDTO {
	private String vendor_name;

	@Nullable
	private String president_name;

	@Nullable
	private String president_phone;

	public VendorDTO(String vendor_name, @Nullable String president_name, @Nullable String president_phone) {
		this.vendor_name = vendor_name;
		this.president_name = president_name;
		this.president_phone = president_phone;
	}

	public VendorDTO(Vendor vendor) {
		this.vendor_name = vendor.getName();
		this.president_name = vendor.getPresidentName();
		this.president_phone = vendor.getPresidentPhone();
	}

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
