package io.saim.dash.global.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import io.saim.dash.global.dto.APIStatus;
import jakarta.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import io.saim.dash.global.dto.CommonResponseDTO.VersionResponseDTO;

@ControllerAdvice
@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "io.saim.dash") // io.saim.dash 내부의 class method return에만 적용
public class CommonResponseResolver implements ResponseBodyAdvice<Object> {

	@Autowired
	private HttpServletRequest request;

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType); // 선택된 converter가 객체 매핑 컨버터인 경우에만 적용
	}

	@Override
	public CommonResponseDTO<?> beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
		Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
		ServerHttpResponse response) {

		//VersionResponseDTO 값이 null이면 기본값을 설정
		VersionResponseDTO version = (VersionResponseDTO) this.request.getAttribute("version");
		if (version == null) {
			version = new VersionResponseDTO("1.0", "1.0");
		}

		CommonResponseDTOBuilder<Object> newResponse = new CommonResponseDTOBuilder<>()
			.version(version);

		newResponse.status(APIStatus.SUCCESS).data(body);
		return newResponse.build();

		// CommonResponseDTO<?> dto = newResponse.build();
		// return ResponseEntity.status(dto.getStatus().getStatusCode()).body(dto);
	}

	@Setter
	@Getter
	@RequiredArgsConstructor
	private static class CommonResponseDTO<T> {
		private final String apiVersion;
		private final String clientVersion;
		private final APIStatus status;
		private final String message;
		private final T data;
	}

	static class CommonResponseDTOBuilder<A> {
		private String apiVersion;
		private String clientVersion;
		private APIStatus status;
		private String message;
		private A data;

		public CommonResponseDTOBuilder<A> version(
			VersionResponseDTO apiVersion) {
			if (apiVersion != null) {
				this.apiVersion = apiVersion.apiVersion();
				this.clientVersion = apiVersion.clientVersion();
			} else {
				this.apiVersion = "1.0";
				this.clientVersion = "1.0";
			}
			return this;
		}

		public CommonResponseDTOBuilder<A> status(APIStatus status) {
			this.status = status;
			return this;
		}

		public CommonResponseDTOBuilder<A> message(String message) {
			this.message = message;
			return this;
		}

		public CommonResponseDTOBuilder<A> data(A data) {
			this.data = data;
			return this;
		}

		public CommonResponseDTO<A> build() {
			return new CommonResponseDTO<>(this.apiVersion, this.clientVersion, this.status, this.message, this.data);
		}
	}
}
