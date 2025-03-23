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
import io.saim.dash.coupon.issue.dto.IssueResultDTO;
import io.saim.dash.coupon.issue.dto.IssueSignRequestDTO;
import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.Product;
import io.saim.dash.coupon.common.model.VendorGroup;
import io.saim.dash.coupon.common.repository.Coupon.CouponRepository;
import io.saim.dash.coupon.common.repository.DUMMY.DUMMY_PartnerUserRepository;
import io.saim.dash.coupon.common.repository.Issue.IssueRepository;
import io.saim.dash.coupon.common.repository.Log.IssueLog.IssueLogRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import io.saim.dash.coupon.common.repository.Product.ProductRepository;
import io.saim.dash.coupon.common.repository.Vendor.VendorRepository;

import io.saim.dash.coupon.common.repository.DUMMY.DUMMY_GeneralUserRepository;

@ExtendWith(MockitoExtension.class)
class IssueServiceTest {

	IssueService issueService;

	@Mock IssueRepository issueRepository;
	@Mock VendorRepository vendorRepository;
	@Mock ProductRepository productRepository;
	@Mock IssueLogRepository issueLogRepository;
	@Mock CouponRepository couponRepository;
	@Mock DUMMY_GeneralUserRepository generalUserRepository;
	@Mock DUMMY_PartnerUserRepository partnerUserRepository;

	@BeforeEach
	void setUp() {
		issueService = new IssueService(
			issueRepository,
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
	void getIssueByPartnerUserTest() {
		// // given
		Issue dummyIssue = new Issue();
		DUMMY_PartnerUser partnerUser = new DUMMY_PartnerUser();
		dummyIssue.setPartner(partnerUser);

		when(issueRepository.findIssuesByPartner(any(DUMMY_PartnerUser.class), any(BooleanBuilder.class), any(Integer.class), any(Integer.class)))
			.thenReturn(List.of(dummyIssue));

		// when
		List<Issue> issues = issueService.getIssuesByUser(
			partnerUser, 0, 0,
			null, null, null, null, null
		);

		System.out.println(issues.size());

		// then
		issues.forEach(v -> {
			Assertions.assertThat(
				v.getPartner().equals(partnerUser)
			).isTrue();
		});
	}

	@Test
	@DisplayName("[로그인: 벤더] 자신이 요청한 발행요청을 조회한다")
	void getIssueByVendorUserTest() {
		// given
		Issue dummyIssue = new Issue();
		VendorGroup vendorGroup = new VendorGroup();
		DUMMY_GeneralUser vendorUser = new DUMMY_GeneralUser();

		vendorUser.addVendor(vendorGroup);
		dummyIssue.setVendorGroup(vendorGroup);

		when(issueRepository.findIssuesByVendor(any(DUMMY_GeneralUser.class), any(BooleanBuilder.class), any(Integer.class), any(Integer.class)))
			.thenReturn(List.of(dummyIssue));

		// when
		List<Issue> issues = issueService.getIssuesByUser(
			vendorUser, 0, 0,
			null, null, null, null, null
		);

		// then
		issues.forEach(v -> {
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
	void getIssueTest_A() {
		// given
		Issue dummyIssue = new Issue();
		VendorGroup vendorGroup = new VendorGroup();
		DUMMY_GeneralUser vendorUser = new DUMMY_GeneralUser();

		vendorUser.addVendor(vendorGroup);
		dummyIssue.setVendorGroup(vendorGroup);

		when(issueRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyIssue));

		// when

		// then
		Assertions.assertThat(
			issueService
				.getIssue(1L, vendorUser)
				.getVendorGroup()
		)
		.isIn(vendorUser.getVendors());
	}

	@Test
	@DisplayName("발행벤더에 소속되지 않은 사용자는 제공된 ID와 일치하는 발행요청을 가져올 수 없다")
	void getIssueTest_B() {
		// given
		Long dummyissueId = 1L;
		Issue dummyIssue = new Issue(); dummyIssue.setIssueId(dummyissueId);
		DUMMY_GeneralUser userA = new DUMMY_GeneralUser();
		DUMMY_GeneralUser userB = new DUMMY_GeneralUser();

		VendorGroup vendorGroup = new VendorGroup();
		userA.addVendor(vendorGroup);
		dummyIssue.setVendorGroup(userA.getVendors().getFirst());

		when(issueRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyIssue));

		// when
		Assertions.assertThatThrownBy(() ->
				issueService.getIssue(dummyissueId, userB)
			)
		// then
			.isInstanceOf(ServiceException.class)
			.hasMessage(ServiceExceptionContent.ISSUE_FORBIDDEN.getMessage());
	}

	@Test
	@DisplayName("일반사용자는 쿠폰의 발행요청서를 생성할 수 있다")
	void createIssueTest() {
		// given
		DUMMY_GeneralUser serviceUser = new DUMMY_GeneralUser();
		List<Long> dummyProducts = List.of(1L, 2L, 3L);

		String vendorName = "", presidentName = "", presidentPhone = "";
		String businessName = "", ownerPhone = "";

		when(productRepository.findAllById(any(List.class)))
			.thenReturn(List.of());

		// when

		Issue createdIssue = issueService.createIssue(serviceUser,
			vendorName, presidentName, presidentPhone, businessName, ownerPhone,
			dummyProducts
		);

		// then
		Assertions.assertThat(
			createdIssue.getVendorGroup()
		).isIn(serviceUser.getVendors());
	}

	@Test
	@DisplayName("일반사용자는 쿠폰 발행요청서를 승인하거나 반려할 수 없다")
	void signIssueTest_A() {
		// given
		DUMMY_GeneralUser serviceUser = new DUMMY_GeneralUser();

		// when
		Assertions.assertThatThrownBy(() ->
				issueService.signIssue(
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
	void signIssueTest_B() {
		// given
		DUMMY_PartnerUser serviceUserA = new DUMMY_PartnerUser();
		DUMMY_PartnerUser serviceUserB = new DUMMY_PartnerUser();

		// when
		Issue dummyIssue = new Issue();
		dummyIssue.setPartner(serviceUserB);

		when(issueRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyIssue));

		// then
		Assertions.assertThatThrownBy(() ->
				issueService.signIssue(
					serviceUserA, 0L,
					null, null, null, null
				)
			)
			.isInstanceOf(ServiceException.class)
			.hasMessage(ServiceExceptionContent.ISSUE_FORBIDDEN.getMessage());
	}

	@Test
	@DisplayName("파트너사용자는 이미 결정한 쿠폰발행요청을 정정할 수 없다")
	void signIssueTest_C() {
		// given
		DUMMY_PartnerUser serviceUserA = new DUMMY_PartnerUser();
		List<IssueSignRequestDTO.IssuePaymentPriceInfo> issuePaymentPriceInfos = List.of(
			new IssueSignRequestDTO.IssuePaymentPriceInfo(1L, 1000L),
			new IssueSignRequestDTO.IssuePaymentPriceInfo(2L, 1000L),
			new IssueSignRequestDTO.IssuePaymentPriceInfo(3L, 1000L)
		);

		// when
		Issue dummyIssue = new Issue();
		dummyIssue.setPartner(serviceUserA);
		dummyIssue.setStatus(IssueStatus.REQUESTED);
		when(issueRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyIssue));

		issueService.signIssue(
			serviceUserA, 0L,
			IssueStatus.APPROVED,
			LocalDateTime.now().toString(),
			issuePaymentPriceInfos,
			0L
		);

		// then
		Assertions.assertThatThrownBy(() ->
				issueService.signIssue(
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
	void signIssueTest_D() {
		// given
		DUMMY_PartnerUser serviceUserA = new DUMMY_PartnerUser();
		List<IssueSignRequestDTO.IssuePaymentPriceInfo> issuePaymentPriceInfos = List.of(
			new IssueSignRequestDTO.IssuePaymentPriceInfo(1L, 1000L),
			new IssueSignRequestDTO.IssuePaymentPriceInfo(2L, 1000L),
			new IssueSignRequestDTO.IssuePaymentPriceInfo(3L, 1000L)
		);

		// when
		Issue dummyIssue = new Issue();
		dummyIssue.setPartner(serviceUserA);
		dummyIssue.setStatus(IssueStatus.REQUESTED);
		dummyIssue.setProducts(List.of(
			Product.builder().build(),
			Product.builder().build(),
			Product.builder().build()
		));

		when(issueRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyIssue));

		IssueResultDTO issueResultDTO = issueService.signIssue(
			serviceUserA, 0L,
			IssueStatus.APPROVED,
			LocalDateTime.now().toString(),
			issuePaymentPriceInfos,
			0L
		);

		// then
		Assertions.assertThat(
			issueResultDTO.issueLog().getIssueCnt()
		).isEqualTo(dummyIssue.getProducts().size());
	}

	@Test
	@DisplayName("파트너사용자는 발행요청서를 삭제할 수 없다")
	void deleteIssueTest_A() {
		// given
		DUMMY_PartnerUser serviceUser = new DUMMY_PartnerUser();

		// when
		Assertions.assertThatThrownBy(() ->
				issueService.deleteIssue(serviceUser, 0L)
			)
		// then
			.isInstanceOf(ServiceException.class)
			.hasMessage(ServiceExceptionContent.NO_PERMISSION.getMessage());
	}

	@Test
	@DisplayName("일반사용자는 자신이 요청한 것이 아닌 발행요청서를 삭제할 수 없다")
	void deleteIssueTest_B() {
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
		Issue dummyIssue = new Issue();
		dummyIssue.setVendorGroup(vendorGroupA);

		when(issueRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyIssue));

		// then
		Assertions.assertThatThrownBy(() ->
				issueService.deleteIssue(serviceUserB, 0L)
			)
			.isInstanceOf(ServiceException.class)
			.hasMessage(ServiceExceptionContent.ISSUE_FORBIDDEN.getMessage());
	}

	@Test
	@DisplayName("일반사용자는 자신이 요청한 발행요청서를 삭제할 수 있다")
	void deleteIssueTest_C() {
		// given
		DUMMY_GeneralUser serviceUser = new DUMMY_GeneralUser();
		VendorGroup vendorGroupA = VendorGroup.builder()
			.presidentName("TEST_VG_PRESIDENT")
			.name("TEST_VG")
			.build();

		serviceUser.addVendor(vendorGroupA);

		// when
		Issue dummyIssue = new Issue();
		dummyIssue.setVendorGroup(vendorGroupA);

		when(issueRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyIssue));

		// then
		Assertions.assertThat(issueService.deleteIssue(serviceUser, 0L))
			.isTrue();
	}
}
