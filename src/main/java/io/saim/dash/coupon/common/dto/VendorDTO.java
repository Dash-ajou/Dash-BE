package io.saim.dash.coupon.common.dto;

import net.minidev.json.annotate.JsonIgnore;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.saim.dash.coupon.common.model.Vendor;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VendorDTO {
	private String vendorName;

	@Nullable
	private String presidentName;

	@Nullable
	private String presidentPhone;

	public VendorDTO(String vendorName, @Nullable String presidentName, @Nullable String presidentPhone) {
		this.vendorName = vendorName;
		this.presidentName = presidentName;
		this.presidentPhone = presidentPhone;
	}

	public VendorDTO(Vendor vendor) {
		this.vendorName = vendor.getName();
		this.presidentName = vendor.getPresidentName();
		this.presidentPhone = vendor.getPresidentPhone();
	}
}
