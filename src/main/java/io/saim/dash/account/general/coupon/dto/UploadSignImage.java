package io.saim.dash.account.general.coupon.dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;

@Builder
public record UploadSignImage(
	MultipartFile sign,
	String register_code
) {
	public String registerCode() {
		return register_code;
	}
}
