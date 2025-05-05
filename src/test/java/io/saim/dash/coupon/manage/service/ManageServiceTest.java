package io.saim.dash.coupon.manage.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import io.saim.dash.coupon.common.repository.Manage.ManageRequestRepository;

class ManageServiceTest {

	ManageService manageService;

	@Mock ManageRequestRepository manageRequestRepository;

	@Test
	@DisplayName("[로그인: 파트너] 본인이 승인하여 발급된 쿠폰들에 대한 발행내역을 조회할 수 있다")
	void getIssuedRequestsByPartnerUserTest_A() {
		// given

		// when

		// then
	}

	@Test
	@DisplayName("[로그인: 벤더] 본인이 요청하여 발급된 쿠폰들에 대한 발행내역을 조회할 수 있다")
	void getIssuedRequestsByVendorUserTest_A() {

	}

	@Test
	@DisplayName("[로그인: 벤더] 본인이 소속된 벤더 내 다른사용자가 요청한 발행내역을 조회할 수 있다")
	void getIssuedRequestsByVendorUserTest_B() {

	}
}
