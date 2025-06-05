package io.saim.dash.account.push.model;

import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.Getter;

public enum PushTag {
	COUPON_EXPIRE_WARNING("쿠폰이 %d일 후 만료됩니다. 만료되기 전 사용해주세요!"),

	COUPON_RECEIVED("%s님으로부터 쿠폰을 받았어요! 지금 확인해보세요."),
	COUPON_USED("%s님의 %s쿠폰 사용이 처리되었습니다."),
	COUPON_USE_CANCELED("%s에 사용된 쿠폰의 사용이 철회되었습니다."),

	REQUEST_RECEIVED("%s로부터 쿠폰 발행요청서가 요청되었습니다. 지금 바로 확인해보세요!"),
	REQUEST_ISSUED("%s에게 요청한 쿠폰 발행요청서가 승인되었습니다."),
	REQUEST_CACELLED("쿠폰 발행요청서가 취소되었습니다."),

	ISSUE_CANCELLED("쿠폰발행이 취소되었습니다.")
	;

	// FORMAT
	@Getter
	private String message;

	PushTag(String message) {
		this.message = message;
	}

	public PushTag replaceArg(Object ...args) {
		this.message = String.format(this.message, args);
		return this;
	}
}
