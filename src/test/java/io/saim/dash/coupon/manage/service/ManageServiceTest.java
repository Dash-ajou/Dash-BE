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
	void getIssuedIRsByPartnerUserTest_A() {
		// given

		// when

		// then
	}

	@Test
	@DisplayName("[로그인: 벤더] 본인이 요청하여 발급된 쿠폰들에 대한 발행내역을 조회할 수 있다")
	void getIssuedIRsByVendorUserTest_A() {

	}

	@Test
	@DisplayName("[로그인: 벤더] 본인이 소속된 벤더 내 다른사용자가 요청한 발행내역을 조회할 수 있다")
	void getIssuedIRsByVendorUserTest_B() {

	}

	@Test
	@DisplayName("[로그인: 파트너] 본인이 승인한 특정 발행요청서로 인해 발행된 쿠폰목록을 조회할 수 있다")
	void getCouponsByIssueIdTest_A() {

	}

	@Test
	@DisplayName("[로그인: 파트너] 본인이 승인하지 않은 쿠폰의 목록을 조회할 수 없다")
	void getCouponsByIssueIdTest_B() {

	}

	@Test
	@DisplayName("[로그인: 벤더] 본인이 요청한 특정 발행요청서로 인해 발행된 쿠폰목록을 조회할 수 있다")
	void getCouponsByIssueIdTest_C() {

	}

	@Test
	@DisplayName("[로그인: 벤더] 본인이 요청하지 않은 쿠폰의 목록을 조회할 수 없다")
	void getCouponsByIssueIdTest_D() {

	}
}
