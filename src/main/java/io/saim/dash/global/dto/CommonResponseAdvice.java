package io.saim.dash.global.dto;

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

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@ControllerAdvice
@RequiredArgsConstructor
@RestControllerAdvice(
	basePackages = "io.saim.dash" // io.saim.dash 내부의 class method return에만 적용
)
public class CommonResponseAdvice implements ResponseBodyAdvice<Object> {

	@Autowired
	private HttpServletRequest request;

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType); // 선택된 converter가 객체 매핑 컨버터인 경우에만 적용
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
		Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
		ServerHttpResponse response) {

		CommonResponseDTOBuilder newResponse = new CommonResponseDTOBuilder()
			.version((VersionResponseDTO) this.request.getAttribute("version"));

		if (body instanceof Error error) {
			newResponse
				.status(APIStatus.FAILURE)
				.message(error.getMessage());
		}

		return newResponse
			.data(body)
			.build();
	}

	@Setter @RequiredArgsConstructor
	static class CommonResponseDTO<T> {
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

		public CommonResponseDTOBuilder<A> version(VersionResponseDTO apiVersion) {
			this.apiVersion = apiVersion.apiVersion;
			this.clientVersion = apiVersion.clientVersion;
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

	public record VersionResponseDTO(
		String apiVersion,
		String clientVersion
	) {}
}
