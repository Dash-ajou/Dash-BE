package io.saim.dash.coupon.issue.service;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.model.IssueRequest;
import io.saim.dash.coupon.common.dto.IssueResultDTO;
import io.saim.dash.coupon.issue.dto.IRSignRequestDTO;
import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.Product;
import io.saim.dash.coupon.common.model.VendorGroup;
import io.saim.dash.coupon.common.repository.Coupon.CouponRepository;
import io.saim.dash.coupon.common.repository.DUMMY.DUMMY_PartnerUserRepository;
import io.saim.dash.coupon.common.repository.IssueRequest.IssueRequestRepository;
import io.saim.dash.coupon.common.repository.Log.IssueLog.IssueLogRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import io.saim.dash.coupon.common.repository.Product.ProductRepository;
import io.saim.dash.coupon.common.repository.Vendor.VendorRepository;

import io.saim.dash.coupon.common.repository.DUMMY.DUMMY_GeneralUserRepository;

@ExtendWith(MockitoExtension.class)
class IssueRequestServiceTest {

	IssueService issueService;

	@Mock
	IssueRequestRepository issueRequestRepository;
	@Mock VendorRepository vendorRepository;
	@Mock ProductRepository productRepository;
	@Mock IssueLogRepository issueLogRepository;
	@Mock CouponRepository couponRepository;
	@Mock DUMMY_GeneralUserRepository generalUserRepository;
	@Mock DUMMY_PartnerUserRepository partnerUserRepository;

	@BeforeEach
	void setUp() {
		issueService = new IssueService(
			issueRequestRepository,
			vendorRepository,
			productRepository,
			issueLogRepository,
			couponRepository,
			generalUserRepository,
			partnerUserRepository
		);
	}

	@Test
	@DisplayName("[로그인: 파트너] 자신에게 들어온 발행요청을 조회한다")
	void getIssueRequestByPartnerUserTest() {
		// // given
		IssueRequest dummyIssueRequest = new IssueRequest();
		DUMMY_PartnerUser partnerUser = new DUMMY_PartnerUser();
		dummyIssueRequest.setPartner(partnerUser);

		when(issueRequestRepository.findIssuesByPartner(any(DUMMY_PartnerUser.class), any(BooleanBuilder.class), any(Integer.class), any(Integer.class)))
			.thenReturn(List.of(dummyIssueRequest));

		// when
		List<IssueRequest> issueRequests = issueService.getIssueRequestsByUser(
			partnerUser, 0, 0,
			null, null, null, null, null
		);

		System.out.println(issueRequests.size());

		// then
		issueRequests.forEach(v -> {
			Assertions.assertThat(
				v.getPartner().equals(partnerUser)
			).isTrue();
		});
	}

	@Test
	@DisplayName("[로그인: 벤더] 자신이 요청한 발행요청을 조회한다")
	void getIssueRequestByVendorUserTest() {
		// given
		IssueRequest dummyIssueRequest = new IssueRequest();
		VendorGroup vendorGroup = new VendorGroup();
		DUMMY_GeneralUser vendorUser = new DUMMY_GeneralUser();

		vendorUser.addVendor(vendorGroup);
		dummyIssueRequest.setVendorGroup(vendorGroup);

		when(issueRequestRepository.findIssuesByVendor(any(DUMMY_GeneralUser.class), any(BooleanBuilder.class), any(Integer.class), any(Integer.class)))
			.thenReturn(List.of(dummyIssueRequest));

		// when
		List<IssueRequest> issueRequests = issueService.getIssueRequestsByUser(
			vendorUser, 0, 0,
			null, null, null, null, null
		);

		// then
		issueRequests.forEach(v -> {
			VendorGroup requestedVendor = v.getVendorGroup();
			System.out.println(requestedVendor != null);

			Assertions.assertThat(
				vendorUser.getVendors()
						.contains(requestedVendor)
			).isTrue();
		});
	}

	@Test
	@DisplayName("발행벤더에 소속된 사용자는 제공된 ID와 일치하는 발행요청을 가져올 수 있다")
	void getIssueRequestTest_A() {
		// given
		IssueRequest dummyIssueRequest = new IssueRequest();
		VendorGroup vendorGroup = new VendorGroup();
		DUMMY_GeneralUser vendorUser = new DUMMY_GeneralUser();

		vendorUser.addVendor(vendorGroup);
		dummyIssueRequest.setVendorGroup(vendorGroup);

		when(issueRequestRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyIssueRequest));

		// when

		// then
		Assertions.assertThat(
			issueService
				.getIssueRequest(1L, vendorUser)
				.getVendorGroup()
		)
		.isIn(vendorUser.getVendors());
	}

	@Test
	@DisplayName("발행벤더에 소속되지 않은 사용자는 제공된 ID와 일치하는 발행요청을 가져올 수 없다")
	void getIssueRequestTest_B() {
		// given
		Long dummyissueId = 1L;
		IssueRequest dummyIssueRequest = new IssueRequest(); dummyIssueRequest.setRequestId(dummyissueId);
		DUMMY_GeneralUser userA = new DUMMY_GeneralUser();
		DUMMY_GeneralUser userB = new DUMMY_GeneralUser();

		VendorGroup vendorGroup = new VendorGroup();
		userA.addVendor(vendorGroup);
		dummyIssueRequest.setVendorGroup(userA.getVendors().getFirst());

		when(issueRequestRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyIssueRequest));

		// when
		Assertions.assertThatThrownBy(() ->
				issueService.getIssueRequest(dummyissueId, userB)
			)
		// then
			.isInstanceOf(ServiceException.class)
			.hasMessage(ServiceExceptionContent.ISSUE_FORBIDDEN.getMessage());
	}

	@Test
	@DisplayName("일반사용자는 쿠폰의 발행요청서를 생성할 수 있다")
	void createIssueRequestTest() {
		// given
		DUMMY_GeneralUser serviceUser = new DUMMY_GeneralUser();
		List<Long> dummyProducts = List.of(1L, 2L, 3L);

		String vendorName = "", presidentName = "", presidentPhone = "";
		String businessName = "", ownerPhone = "";

		when(productRepository.findAllById(any(List.class)))
			.thenReturn(List.of());

		// when

		IssueRequest createdIssueRequest = issueService.createIssueRequest(serviceUser,
			vendorName, presidentName, presidentPhone, businessName, ownerPhone,
			dummyProducts
		);

		// then
		Assertions.assertThat(
			createdIssueRequest.getVendorGroup()
		).isIn(serviceUser.getVendors());
	}

	@Test
	@DisplayName("일반사용자는 쿠폰 발행요청서를 승인하거나 반려할 수 없다")
	void signIssueRequestTest_A() {
		// given
		DUMMY_GeneralUser serviceUser = new DUMMY_GeneralUser();

		// when
		Assertions.assertThatThrownBy(() ->
				issueService.signIssueRequest(
					serviceUser, 0L,
					null, null, null, null
				)
			)
		// then
			.isInstanceOf(ServiceException.class)
			.hasMessage(ServiceExceptionContent.NO_PERMISSION.getMessage());
	}

	@Test
	@DisplayName("파트너사용자는 본인에게 요청되지 않은 쿠폰 발행요청서를 승인하거나 반려할 수 없다")
	void signIssueRequestTest_B() {
		// given
		DUMMY_PartnerUser serviceUserA = new DUMMY_PartnerUser();
		DUMMY_PartnerUser serviceUserB = new DUMMY_PartnerUser();

		// when
		IssueRequest dummyIssueRequest = new IssueRequest();
		dummyIssueRequest.setPartner(serviceUserB);

		when(issueRequestRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyIssueRequest));

		// then
		Assertions.assertThatThrownBy(() ->
				issueService.signIssueRequest(
					serviceUserA, 0L,
					null, null, null, null
				)
			)
			.isInstanceOf(ServiceException.class)
			.hasMessage(ServiceExceptionContent.ISSUE_FORBIDDEN.getMessage());
	}

	@Test
	@DisplayName("파트너사용자는 이미 결정한 쿠폰발행요청을 정정할 수 없다")
	void signIssueRequestTest_C() {
		// given
		DUMMY_PartnerUser serviceUserA = new DUMMY_PartnerUser();
		List<IRSignRequestDTO.IssuePaymentPriceInfo> issuePaymentPriceInfos = List.of(
			new IRSignRequestDTO.IssuePaymentPriceInfo(1L, 1000L),
			new IRSignRequestDTO.IssuePaymentPriceInfo(2L, 1000L),
			new IRSignRequestDTO.IssuePaymentPriceInfo(3L, 1000L)
		);

		// when
		IssueRequest dummyIssueRequest = new IssueRequest();
		dummyIssueRequest.setPartner(serviceUserA);
		dummyIssueRequest.setVendorGroup(new VendorGroup());
		dummyIssueRequest.setStatus(IssueStatus.REQUESTED);
		when(issueRequestRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyIssueRequest));

		issueService.signIssueRequest(
			serviceUserA, 0L,
			IssueStatus.APPROVED,
			LocalDateTime.now().toString(),
			issuePaymentPriceInfos,
			0L
		);

		// then
		Assertions.assertThatThrownBy(() ->
				issueService.signIssueRequest(
					serviceUserA, 0L,
					IssueStatus.APPROVED,
					LocalDateTime.now().toString(),
					issuePaymentPriceInfos,
					0L
				)
			)
			.isInstanceOf(ServiceException.class)
			.hasMessage(ServiceExceptionContent.ISSUE_ALREADY_SIGNED.getMessage());
	}

	@Test
	@DisplayName("파트너사용자가 쿠폰 발행요청서를 승인하면, 쿠폰은 발행요청서의 요청수량만큼 자동발행된다")
	void signIssueRequestTest_D() {
		// given
		DUMMY_PartnerUser serviceUserA = new DUMMY_PartnerUser();
		List<IRSignRequestDTO.IssuePaymentPriceInfo> issuePaymentPriceInfos = List.of(
			new IRSignRequestDTO.IssuePaymentPriceInfo(1L, 1000L),
			new IRSignRequestDTO.IssuePaymentPriceInfo(2L, 1000L),
			new IRSignRequestDTO.IssuePaymentPriceInfo(3L, 1000L)
		);

		// when
		IssueRequest dummyIssueRequest = new IssueRequest();
		dummyIssueRequest.setPartner(serviceUserA);
		dummyIssueRequest.setVendorGroup(new VendorGroup());
		dummyIssueRequest.setStatus(IssueStatus.REQUESTED);
		dummyIssueRequest.setProducts(List.of(
			Product.builder().build(),
			Product.builder().build(),
			Product.builder().build()
		));

		when(issueRequestRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyIssueRequest));

		IssueResultDTO issueResultDTO = issueService.signIssueRequest(
			serviceUserA, 0L,
			IssueStatus.APPROVED,
			LocalDateTime.now().toString(),
			issuePaymentPriceInfos,
			0L
		);

		// then
		Assertions.assertThat(
			issueResultDTO.getIssueCount()
		).isEqualTo(dummyIssueRequest.getProducts().size());
	}

	@Test
	@DisplayName("파트너사용자는 발행요청서를 삭제할 수 없다")
	void deleteIssueRequestTest_A() {
		// given
		DUMMY_PartnerUser serviceUser = new DUMMY_PartnerUser();

		// when
		Assertions.assertThatThrownBy(() ->
				issueService.deleteIssueRequest(serviceUser, 0L)
			)
		// then
			.isInstanceOf(ServiceException.class)
			.hasMessage(ServiceExceptionContent.NO_PERMISSION.getMessage());
	}

	@Test
	@DisplayName("일반사용자는 자신이 요청한 것이 아닌 발행요청서를 삭제할 수 없다")
	void deleteIssueRequestTest_B() {
		// given
		DUMMY_GeneralUser serviceUserA = new DUMMY_GeneralUser();
		DUMMY_GeneralUser serviceUserB = new DUMMY_GeneralUser();
		VendorGroup vendorGroupA = VendorGroup.builder()
			.presidentName("TEST_VG_PRESIDENT")
			.name("TEST_VG")
			.build();
		VendorGroup vendorGroupB = VendorGroup.builder()
			.presidentName("TEST_VG_PRESIDENTB")
			.name("TEST_VGB")
			.build();

		serviceUserA.addVendor(vendorGroupA);
		serviceUserB.addVendor(vendorGroupB);

		// when
		IssueRequest dummyIssueRequest = new IssueRequest();
		dummyIssueRequest.setVendorGroup(vendorGroupA);

		when(issueRequestRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyIssueRequest));

		// then
		Assertions.assertThatThrownBy(() ->
				issueService.deleteIssueRequest(serviceUserB, 0L)
			)
			.isInstanceOf(ServiceException.class)
			.hasMessage(ServiceExceptionContent.ISSUE_FORBIDDEN.getMessage());
	}

	@Test
	@DisplayName("일반사용자는 자신이 요청한 발행요청서를 삭제할 수 있다")
	void deleteIssueRequestTest_C() {
		// given
		DUMMY_GeneralUser serviceUser = new DUMMY_GeneralUser();
		VendorGroup vendorGroupA = VendorGroup.builder()
			.presidentName("TEST_VG_PRESIDENT")
			.name("TEST_VG")
			.build();

		serviceUser.addVendor(vendorGroupA);

		// when
		IssueRequest dummyIssueRequest = new IssueRequest();
		dummyIssueRequest.setVendorGroup(vendorGroupA);

		when(issueRequestRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyIssueRequest));

		// then
		Assertions.assertThat(issueService.deleteIssueRequest(serviceUser, 0L))
			.isTrue();
	}
}
