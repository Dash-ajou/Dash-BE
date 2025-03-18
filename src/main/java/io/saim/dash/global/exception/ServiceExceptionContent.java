package io.saim.dash.global.exception;

import java.util.Collections;

import io.saim.dash.global.dto.APIStatus;
import lombok.Getter;

@Getter
public enum ServiceExceptionContent {

	// 400
	// DEFAULT_BAD_REQUEST(APIStatus.BAD_REQUEST, "잘못된 요청입니다. 확인 후 다시 시도해주세요."),

	// 401
	// UNAUTHORIZED(APIStatus.UNAUTHORIZED, "인증되지 않았습니다"),
	// AUTHORIZATION_EXPIRED(APIStatus.UNAUTHORIZED, "인증이 만료되었습니다. 잠시 후 다시 시도해주세요"),

	// 403
	// BLOCKED_USER(APIStatus.FORBIDDEN, "차단된 사용자입니다. 관리자에게 문의해주세요"),
	// 400
	INVALID_COUPON(APIStatus.BAD_REQUEST, "유효하지 않은 쿠폰입니다."),
	COUPON_ALREADY_USED(APIStatus.BAD_REQUEST, "이미 사용된 쿠폰입니다."),
	COUPON_TRANSFER_NOT_ALLOWED(APIStatus.FORBIDDEN, "해당 쿠폰을 양도할 권한이 없습니다."),
	INVALID_INPUT(APIStatus.BAD_REQUEST, "잘못된 입력값입니다. 확인 후 다시 시도해주세요.: %s"),

	//401
	UNAUTHORIZED(APIStatus.UNAUTHORIZED, "인증되지 않았습니다."),
	// 404
	COUPON_NOT_FOUND(APIStatus.NOT_FOUND, "쿠폰이 존재하지 않습니다."),
	USER_NOT_FOUND(APIStatus.NOT_FOUND, "수신자가 존재하지 않습니다."),

	TEST_METHOD_REQUESTED(APIStatus.FORBIDDEN, "사용할 수 없는 요청입니다: %s"),

	// 404
	// DATA_NOT_FOUND(APIStatus.NOT_FOUND, "일치하는 데이터ID를 찾을 수 없습니다: %d"),


	// 500
	INTERNAL_SERVER_ERROR(APIStatus.FAILURE, "알 수 없는 오류가 발생하였습니다. 잠시 후 다시 시도헤주세요."),

	;

	// FORMAT
	private final APIStatus apiStatus;
	private String message;

	ServiceExceptionContent(APIStatus apiStatus, String message) {
		this.apiStatus = apiStatus;
		this.message = message;
	}

	public ServiceExceptionContent replaceArg(Object ...args) {
		this.message = String.format(this.message, args);
		return this;
	}
}
