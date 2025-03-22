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

import io.saim.dash.coupon.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.model.Issue;
import io.saim.dash.coupon.model.VendorGroup;
import io.saim.dash.coupon.repository.Issue.IssueRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;

@ExtendWith(MockitoExtension.class)
class IssueServiceTest {

	IssueService issueService;

	@Mock
	IssueRepository issueRepository;

	@BeforeEach
	void setUp() {
		issueService = new IssueService(issueRepository);
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
		Issue dummyIssue = new Issue(); dummyIssue.setId(dummyissueId);
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
}
