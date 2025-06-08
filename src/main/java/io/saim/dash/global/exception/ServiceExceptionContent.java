package io.saim.dash.global.exception;

import io.saim.dash.global.dto.APIStatus;
import lombok.Getter;

@Getter
public enum ServiceExceptionContent {

	// 200
	IMAGE_NOT_READY(APIStatus.ACCEPTED, "이미지가 아직 처리중입니다. 잠시 후 다시 시도해주세요."),

	// 400
	DEFAULT_BAD_REQUEST(APIStatus.BAD_REQUEST, "잘못된 요청입니다. 확인 후 다시 시도해주세요."),
	ISSUE_ALREADY_SIGNED(APIStatus.BAD_REQUEST, "이미 승인/반려여부가 결정된 발행요청입니다."),
	BAD_ISSUE_SIGN_REQUEST(APIStatus.BAD_REQUEST, "올바르지 않은 발행승인/반려 요청입니다."),
	ACTIVE_STATUS_ALREADY_UPDATED(APIStatus.BAD_REQUEST, "현재 상태와 요청하신 상태가 동일합니다."),
	ISSUE_NOT_DISABLED(APIStatus.BAD_REQUEST, "발행 건에 대하여 일시정지가 되지 않았습니다."),
	INVALID_PAYMENT_CODE(APIStatus.BAD_REQUEST, "올바르지 않은 결제코드 형식입니다."),
	INVALID_ISSUE_STATUS(APIStatus.BAD_REQUEST, "사용할 수 없는 쿠폰상태입니다. 쿠폰 발행처에 문의해주세요." ),
	INVALID_COUPON_STATUS(APIStatus.BAD_REQUEST, "사용할 수 없는 쿠폰상태입니다. 쿠폰상태를 확인해주세요." ),
	INVALID_COUPON(APIStatus.BAD_REQUEST, "유효하지 않은 쿠폰입니다."),
	COUPON_ALREADY_USED(APIStatus.BAD_REQUEST, "이미 사용된 쿠폰입니다."),
	COUPON_TRANSFER_NOT_ALLOWED(APIStatus.FORBIDDEN, "해당 쿠폰을 양도할 권한이 없습니다."),
	ALREADY_REGISTERED(APIStatus.BAD_REQUEST, "이미 등록된 쿠폰입니다."),
	DUPLICATE_PHONE(APIStatus.BAD_REQUEST, "이미 등록된 전화번호입니다."),
	INVALID_INPUT(APIStatus.BAD_REQUEST, "잘못된 입력값입니다. 확인 후 다시 시도해주세요."),
	PAYMENT_CODE_EXPIRED(APIStatus.BAD_REQUEST, "결제코드가 만료되었습니다. 확인 후 다 시 시도해주세요."),
	ALREADY_PROCESSED(APIStatus.BAD_REQUEST, "이미 처리된 쿠폰 요청입니다."),
	USER_HAS_COUPONS(APIStatus.FORBIDDEN, "등록한 쿠폰이 있어 탈퇴할 수 없습니다."),

	// 401
  	UNAUTHORIZED(APIStatus.UNAUTHORIZED, "인증되지 않았습니다."),
	INVALID_VERIFICATION_NUMBER(APIStatus.UNAUTHORIZED, "올바르지 않은 인증번호입니다."),
	// AUTHORIZATION_EXPIRED(APIStatus.UNAUTHORIZED, "인증이 만료되었습니다. 잠시 후 다시 시도해주세요"),

	// 403
  	NO_PERMISSION(APIStatus.FORBIDDEN, "요청한 작업에 대한 권한이 없습니다."),
	ISSUE_FORBIDDEN(APIStatus.FORBIDDEN, "접근권한이 없는 이슈입니다."),
  	TEST_METHOD_REQUESTED(APIStatus.FORBIDDEN, "사용할 수 없는 요청입니다: %s"),
	// BLOCKED_USER(APIStatus.FORBIDDEN, "차단된 사용자입니다. 관리자에게 문의해주세요"),

	// 404
	COUPON_NOT_FOUND(APIStatus.NOT_FOUND, "쿠폰이 존재하지 않습니다."),
	USER_NOT_FOUND(APIStatus.NOT_FOUND, "수신자가 존재하지 않습니다."),
	PAYMENT_CODE_NOT_FOUND(APIStatus.NOT_FOUND, "결제코드가 존재하지 않습니다."),
	// DATA_NOT_FOUND(APIStatus.NOT_FOUND, "일치하는 데이터ID를 찾을 수 없습니다: %d"),
	REQUEST_NOT_FOUND(APIStatus.NOT_FOUND, "요청하신 발행요청서를 찾을 수 없습니다."),
	IMAGE_NOT_FOUND(APIStatus.NOT_FOUND, "요청하신 이미지를 찾을 수 없습니다."),
	ISSUE_NOT_FOUND(APIStatus.NOT_FOUND, "요청하신 이슈를 찾을 수 없습니다."),
	PRODUCT_NOT_FOUND(APIStatus.NOT_FOUND, "요청하신 제품을 찾을 수 없습니다."),
	PAYMENT_LOG_NOT_FOUND(APIStatus.NOT_FOUND, "결제기록을 찾을 수 없습니다."),
	PUSH_NOT_FOUND(APIStatus.NOT_FOUND, "요청하신 Push 메시지를 찾을 수 없습니다."),
	VENDOR_NOT_FOUND(APIStatus.NOT_FOUND, "해당 발급 단체(Vendor)를 찾을 수 없습니다."),


	// 500
	INTERNAL_SERVER_ERROR(APIStatus.FAILED, "알 수 없는 오류가 발생하였습니다. 잠시 후 다시 시도헤주세요."),
	FILE_SAVE_ERROR(APIStatus.FAILED, "파일 저장 중 문제가 발생하였습니다. 잠시 후 다시 시도해주세요."),
	FILE_GET_ERROR(APIStatus.FAILED, "파일 조회 중 문제가 발생하였습니다. 잠시 후 다시 시도해주세요."),

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
