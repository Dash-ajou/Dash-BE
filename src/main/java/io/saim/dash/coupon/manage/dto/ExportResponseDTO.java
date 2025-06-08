package io.saim.dash.coupon.manage.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.saim.dash.coupon.common.constant.CouponExportType;
import io.saim.dash.coupon.common.model.Coupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExportResponseDTO {
	private Long requestId;
	private CouponExportType exportType;
	private String downloadUrl;

	public ExportResponseDTO(List<Coupon> coupons) {

	}
}
