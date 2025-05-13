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
import io.saim.dash.coupon.common.dto.Request.RequestProductCountDTO;
import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.dto.Issue.IssueResultDTO;
import io.saim.dash.coupon.common.model.Vendor;
import io.saim.dash.coupon.common.model.mapping.RequestProduct;
import io.saim.dash.coupon.common.repository.jpa.IssueJpaRepository;
import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.Product;
import io.saim.dash.coupon.common.repository.DUMMY.DUMMY_PartnerUserRepository;
import io.saim.dash.coupon.common.repository.Request.RequestRepository;
import io.saim.dash.coupon.common.dto.Request.RequestProductPriceDTO;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import io.saim.dash.coupon.common.repository.Product.ProductRepository;
import io.saim.dash.coupon.common.repository.Vendor.VendorRepository;

@ExtendWith(MockitoExtension.class)
class IssueServiceTest {

	IssueService issueService;

	@Mock RequestRepository requestRepository;
	@Mock IssueJpaRepository issueJpaRepository;
	@Mock VendorRepository vendorRepository;
	@Mock ProductRepository productRepository;
	@Mock DUMMY_PartnerUserRepository partnerUserRepository;

	@BeforeEach
	void setUp() {
		issueService = new IssueService(
			requestRepository,
			issueJpaRepository,
			vendorRepository,
			productRepository,
			partnerUserRepository
		);
	}

	@Test
	@DisplayName("[로그인: 파트너] 자신에게 들어온 발행요청을 조회한다")
	void getIssueRequestByPartnerUserTest() {
		// // given
		Request dummyRequest = new Request();
		DUMMY_PartnerUser partnerUser = new DUMMY_PartnerUser();
		dummyRequest.setPartner(partnerUser);

		when(requestRepository.findRequestsByPartner(any(DUMMY_PartnerUser.class), any(BooleanBuilder.class), any(Integer.class), any(Integer.class)))
			.thenReturn(List.of(dummyRequest));

		// when
		List<Request> requests = issueService.getRequestsByPartner(
			partnerUser, 0, 0,
			null, null, null, null, null
		);

		System.out.println(requests.size());

		// then
		requests.forEach(v -> {
			Assertions.assertThat(
				v.getPartner().equals(partnerUser)
			).isTrue();
		});
	}

	@Test
	@DisplayName("[로그인: 벤더] 자신이 요청한 발행요청을 조회한다")
	void getIssueRequestByVendorUserTest() {
		// given
		Request dummyRequest = new Request();
		Vendor vendor = new Vendor();
		DUMMY_GeneralUser vendorUser = new DUMMY_GeneralUser();

		vendorUser.addVendor(vendor);
		dummyRequest.setVendor(vendor);

		when(requestRepository.findRequestsByVendor(any(DUMMY_GeneralUser.class), any(BooleanBuilder.class), any(Integer.class), any(Integer.class)))
			.thenReturn(List.of(dummyRequest));

		// when
		List<Request> requests = issueService.getRequestsByPartner(
			vendorUser, 0, 0,
			null, null, null, null, null
		);

		// then
		requests.forEach(v -> {
			Vendor requestedVendor = v.getVendor();
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
		Request dummyRequest = new Request();
		Vendor vendor = new Vendor();
		DUMMY_GeneralUser vendorUser = new DUMMY_GeneralUser();

		vendorUser.addVendor(vendor);
		dummyRequest.setVendor(vendor);

		when(requestRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyRequest));

		// when

		// then
		Assertions.assertThat(
			issueService
				.getRequest(1L, vendorUser)
				.getVendor()
		)
		.isIn(vendorUser.getVendors());
	}

	@Test
	@DisplayName("발행벤더에 소속되지 않은 사용자는 제공된 ID와 일치하는 발행요청을 가져올 수 없다")
	void getIssueRequestTest_B() {
		// given
		Long dummyissueId = 1L;
		Request dummyRequest = new Request(); dummyRequest.setRequestId(dummyissueId);
		DUMMY_GeneralUser userA = new DUMMY_GeneralUser();
		DUMMY_GeneralUser userB = new DUMMY_GeneralUser();

		Vendor vendor = new Vendor();
		userA.addVendor(vendor);
		dummyRequest.setVendor(userA.getVendors().getFirst());

		when(requestRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyRequest));

		// when
		Assertions.assertThatThrownBy(() ->
				issueService.getRequest(dummyissueId, userB)
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
		List<RequestProductCountDTO> dummyRequestProducts = List.of(
			new RequestProductCountDTO(1L, 2L),
			new RequestProductCountDTO(2L, 10L),
			new RequestProductCountDTO(3L, 30L)
		);

		String vendorName = "", presidentName = "", presidentPhone = "";
		String businessName = "", ownerPhone = "";

		when(productRepository.findAllById(any(List.class)))
			.thenReturn(List.of());

		// when

		Request createdRequest = issueService.createIssueRequest(serviceUser,
			vendorName, presidentName, presidentPhone, businessName, ownerPhone,
			dummyRequestProducts
		);

		// then
		Assertions.assertThat(
			createdRequest.getVendor()
		).isIn(serviceUser.getVendors());
	}

	@Test
	@DisplayName("일반사용자는 쿠폰 발행요청서를 승인하거나 반려할 수 없다")
	void signIssueRequestTest_A() {
		// given
		DUMMY_GeneralUser serviceUser = new DUMMY_GeneralUser();

		// when
		Assertions.assertThatThrownBy(() ->
				issueService.signRequest(
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
		Request dummyRequest = new Request();
		dummyRequest.setPartner(serviceUserB);

		when(requestRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyRequest));

		// then
		Assertions.assertThatThrownBy(() ->
				issueService.signRequest(
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
		List<RequestProductPriceDTO> requestProductPriceDTOS = List.of(
			new RequestProductPriceDTO(1L, 1000L),
			new RequestProductPriceDTO(2L, 1000L),
			new RequestProductPriceDTO(3L, 1000L)
		);

		// when
		Request dummyRequest = new Request();
		dummyRequest.setPartner(serviceUserA);
		dummyRequest.setVendor(new Vendor());
		dummyRequest.setStatus(IssueStatus.REQUESTED);
		when(requestRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyRequest));

		issueService.signRequest(
			serviceUserA, 0L,
			IssueStatus.APPROVED,
			LocalDateTime.now().toString(),
			requestProductPriceDTOS,
			0L
		);

		// then
		Assertions.assertThatThrownBy(() ->
				issueService.signRequest(
					serviceUserA, 0L,
					IssueStatus.APPROVED,
					LocalDateTime.now().toString(),
					requestProductPriceDTOS,
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
		List<RequestProductPriceDTO> requestProductPriceDTOS = List.of(
			new RequestProductPriceDTO(1L, 1000L),
			new RequestProductPriceDTO(2L, 1000L),
			new RequestProductPriceDTO(3L, 1000L)
		);

		// when
		Request dummyRequest = new Request();
		dummyRequest.setPartner(serviceUserA);
		dummyRequest.setVendor(new Vendor());
		dummyRequest.setStatus(IssueStatus.REQUESTED);

		DUMMY_PartnerUser serviceUserB = new DUMMY_PartnerUser();
		Product productA = new Product(serviceUserB, "sefes", 50000L);
		productA.setProductId(1L);
		Product productB = new Product(serviceUserB, "sefes", 50000L);
		productB.setProductId(2L);
		Product productC = new Product(serviceUserB, "sefes", 50000L);
		productC.setProductId(3L);

		dummyRequest.setRequestProducts(List.of(
			RequestProduct.builder().product(productA).build(),
			RequestProduct.builder().product(productB).build(),
			RequestProduct.builder().product(productC).build()
		));

		when(requestRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyRequest));

		IssueResultDTO issueResultDTO = issueService.signRequest(
			serviceUserA, 0L,
			IssueStatus.APPROVED,
			LocalDateTime.now().toString(),
			requestProductPriceDTOS,
			0L
		);

		// then
		Assertions.assertThat(
			issueResultDTO.getIssue().getIssueCnt()
		).isEqualTo(dummyRequest.getRequestProducts().size());
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
		Vendor vendorA = Vendor.builder()
			.presidentName("TEST_VG_PRESIDENT")
			.name("TEST_VG")
			.build();
		Vendor vendorB = Vendor.builder()
			.presidentName("TEST_VG_PRESIDENTB")
			.name("TEST_VGB")
			.build();

		serviceUserA.addVendor(vendorA);
		serviceUserB.addVendor(vendorB);

		// when
		Request dummyRequest = new Request();
		dummyRequest.setVendor(vendorA);

		when(requestRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyRequest));

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
		Vendor vendorA = Vendor.builder()
			.presidentName("TEST_VG_PRESIDENT")
			.name("TEST_VG")
			.build();

		serviceUser.addVendor(vendorA);

		// when
		Request dummyRequest = new Request();
		dummyRequest.setVendor(vendorA);

		when(requestRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyRequest));

		// then
		Assertions.assertThat(issueService.deleteIssueRequest(serviceUser, 0L))
			.isTrue();
	}
}
