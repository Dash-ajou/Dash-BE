package io.saim.dash.global.exception;

import io.saim.dash.global.dto.APIStatus;
import lombok.Getter;

@Getter
public enum ServiceExceptionContent {

	// 400
	DEFAULT_BAD_REQUEST(APIStatus.BAD_REQUEST, "잘못된 요청입니다. 확인 후 다시 시도해주세요."),
	ISSUE_ALREADY_SIGNED(APIStatus.BAD_REQUEST, "이미 승인/반려여부가 결정된 발행요청입니다."),
	BAD_ISSUE_SIGN_REQUEST(APIStatus.BAD_REQUEST, "올바르지 않은 발행승인/반려 요청입니다."),
	ACTIVE_STATUS_ALREADY_UPDATED(APIStatus.BAD_REQUEST, "현재 상태와 요청하신 상태가 동일합니다."),
	ISSUE_IS_ENABLED(APIStatus.BAD_REQUEST, "발행 건에 대하여 일시정지가 되지 않았습니다."),

	// 401
	// UNAUTHORIZED(APIStatus.UNAUTHORIZED, "인증되지 않았습니다"),
	// AUTHORIZATION_EXPIRED(APIStatus.UNAUTHORIZED, "인증이 만료되었습니다. 잠시 후 다시 시도해주세요"),

	// 403
	// BLOCKED_USER(APIStatus.FORBIDDEN, "차단된 사용자입니다. 관리자에게 문의해주세요"),
	NO_PERMISSION(APIStatus.FORBIDDEN, "요청한 작업에 대한 권한이 없습니다."),
	ISSUE_FORBIDDEN(APIStatus.FORBIDDEN, "접근권한이 없는 이슈입니다."),
	TEST_METHOD_REQUESTED(APIStatus.FORBIDDEN, "사용할 수 없는 요청입니다: %s"),

	// 404
	// DATA_NOT_FOUND(APIStatus.NOT_FOUND, "일치하는 데이터ID를 찾을 수 없습니다: %d"),
	ISSUE_NOT_FOUND(APIStatus.NOT_FOUND, "요청하신 이슈를 찾을 수 없습니다."),
	PRODUCT_NOT_FOUND(APIStatus.NOT_FOUND, "요청하신 제품을 찾을 수 없습니다."),


	// 500
	INTERNAL_SERVER_ERROR(APIStatus.FAILED, "알 수 없는 오류가 발생하였습니다. 잠시 후 다시 시도헤주세요."),

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
