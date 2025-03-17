package io.saim.dash.coupon.issue.service;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
		// given
		Issue dummyIssue = mock(Issue.class);
		when(issueRepository.getIssuesByPartner(any(DUMMY_PartnerUser.class)))
			.thenReturn(List.of(dummyIssue));

		// when
		DUMMY_PartnerUser partnerUser = new DUMMY_PartnerUser();
		List<Issue> issues = issueService.getIssuesByUser(partnerUser);

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
		Issue dummyIssue = mock(Issue.class);
		when(issueRepository.getIssuesByVendor(any(DUMMY_GeneralUser.class)))
			.thenReturn(List.of(dummyIssue));

		// when
		DUMMY_GeneralUser vendorUser = new DUMMY_GeneralUser();
		List<Issue> issues = issueService.getIssuesByUser(vendorUser);

		// then
		issues.forEach(v -> {
			VendorGroup requestedVendor = v.getVendorGroup();

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
		Issue dummyIssue = mock(Issue.class);
		when(issueRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyIssue));

		// when
		DUMMY_GeneralUser userA = new DUMMY_GeneralUser();
		DUMMY_GeneralUser userB = new DUMMY_GeneralUser();

		Issue searchedIssue = issueService.getIssue(dummyIssue.getId(), userA);

		// then
		Assertions.assertThat(searchedIssue).isEqualTo(dummyIssue);
	}

	@Test
	@DisplayName("발행벤더에 소속되지 않은 사용자는 제공된 ID와 일치하는 발행요청을 가져올 수 없다")
	void getIssueTest_B() {
		// given
		Issue dummyIssue = mock(Issue.class);
		when(issueRepository.getById(any(Long.class)))
			.thenReturn(Optional.of(dummyIssue));

		// when
		DUMMY_GeneralUser userA = new DUMMY_GeneralUser();
		DUMMY_GeneralUser userB = new DUMMY_GeneralUser();

		// then
		Assertions.assertThatThrownBy(() ->
				issueService.getIssue(dummyIssue.getId(), userA)
			)
			.isInstanceOf(ServiceException.class)
			.hasMessage(ServiceExceptionContent.ISSUE_FORBIDDEN.getMessage());
	}
}
