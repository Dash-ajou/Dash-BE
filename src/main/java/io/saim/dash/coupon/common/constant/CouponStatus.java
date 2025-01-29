package io.saim.dash.coupon.common.constant;

public enum CouponStatus {
	REGISTERABLE, // 등록가능
	USABLE, // 사용가능
	USED, // 사용완료
	EXPIRED, // 사용기간 만료
	DISABLED, // 일시정지
	CANCELED // 발행취소
}
