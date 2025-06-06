package io.saim.dash.global.resolver;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import io.saim.dash.global.dto.CommonResponseDTO.VersionResponseDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

		if (body instanceof CommonResponseDTO) {
			return (CommonResponseDTO<?>) body;
		}

		CommonResponseDTO.CommonResponseDTOBuilder<Object> newResponse = CommonResponseDTO.builder();
		addVersionInfo(newResponse);

		newResponse.status(APIStatus.SUCCESS).data(body);
		return newResponse.build();

		// CommonResponseDTO<?> dto = newResponse.build();
		// return ResponseEntity.status(dto.getStatus().getStatusCode()).body(dto);
	}

	@NotNull
	private void addVersionInfo(CommonResponseDTO.CommonResponseDTOBuilder<?> responseBuilder) {
		VersionResponseDTO version = (VersionResponseDTO) this.request.getAttribute("version");
		if (version == null) {
			version = new VersionResponseDTO("1.0", "1.0");
		}

		responseBuilder.apiVersion(version.apiVersion());
		responseBuilder.clientVersion(version.clientVersion());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public ResponseEntity<CommonResponseDTO<?>> handleJsonParseError(
		HttpMessageNotReadableException ex, HttpServletRequest request
	) {
		log.error(ex.getMessage());
		log.error(ex.getClass().getSimpleName(), ex);

		String message = "요청 본문 형식이 잘못되었습니다.";
		Throwable cause = ex.getCause();
		if (cause instanceof InvalidFormatException) {
			message = "잘못된 값이 포함되어 있습니다";
		}

		CommonResponseDTO.CommonResponseDTOBuilder<Object> newResponse = CommonResponseDTO.builder();
		addVersionInfo(newResponse);

		newResponse.status(APIStatus.BAD_REQUEST).message(message).data(null);

		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(newResponse.build());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseBody
	public ResponseEntity<CommonResponseDTO<?>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		log.error(ex.getMessage());
		log.error(ex.getClass().getSimpleName(), ex);

		CommonResponseDTO.CommonResponseDTOBuilder<Object> newResponse = CommonResponseDTO.builder();
		addVersionInfo(newResponse);

		newResponse.status(APIStatus.BAD_REQUEST).message("요청 파라미터 타입이 잘못되었습니다").data(null);

		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(newResponse.build());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<CommonResponseDTO<?>> handleValidationError(
		MethodArgumentNotValidException ex
	) {
		FieldError fieldError = ex.getBindingResult().getFieldError();
		CommonResponseDTO.CommonResponseDTOBuilder<Object> newResponse = CommonResponseDTO.builder();
		addVersionInfo(newResponse);

		String fieldErrorMessage = "필드정보 없음";
		if (fieldError != null) fieldErrorMessage = fieldError.getDefaultMessage();

		log.error(ex.getMessage(), fieldErrorMessage);
		log.error(ex.getClass().getSimpleName(), ex);

		newResponse.status(APIStatus.BAD_REQUEST).message("필드 유효성 검사 실패").data(null);

		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(newResponse.build());
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<CommonResponseDTO<?>> handleGeneral(Exception ex) {
		log.error("[ERROR - CommonResponseResolver]", ex);

		CommonResponseDTO.CommonResponseDTOBuilder<Object> newResponse = CommonResponseDTO.builder();
		addVersionInfo(newResponse);

		if (ex instanceof ServiceException e) {
			newResponse.status(e.getApiStatus()).message(e.getMessage()).data(null);
			return ResponseEntity
				.status(e.getApiStatus().getStatusCode())
				.body(newResponse.build());
		}

		String errorMessage = ex.getMessage();
		if (errorMessage == null || errorMessage.isEmpty()) errorMessage = "알 수 없는 오류가 발생하였습니다";

		newResponse.status(APIStatus.FAILED).message(errorMessage).data(null);
		return ResponseEntity
			.status(APIStatus.FAILED.getStatusCode())
			.body(newResponse.build());
	}
}
