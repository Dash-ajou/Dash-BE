package io.saim.dash.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponseDTO<T> {

	public record VersionResponseDTO(
		String apiVersion,
		String clientVersion
	) {}

	private final String apiVersion;
	private final String clientVersion;
	private final APIStatus status;
	private final String message;
	@Getter
	private final T data;

	public CommonResponseDTO(VersionResponseDTO version, APIStatus status, String message, T data) {
		this.apiVersion = (version != null) ? version.apiVersion() : "1.0";
		this.clientVersion = (version != null) ? version.clientVersion() : "default";

		this.status = status;
		this.message = message;
		this.data = data;
	}

}
