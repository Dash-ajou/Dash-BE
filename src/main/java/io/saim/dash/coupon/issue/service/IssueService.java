package io.saim.dash.coupon.issue.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.model.DUMMY_ServiceUser;
import io.saim.dash.coupon.model.Issue;
import io.saim.dash.coupon.model.QIssue;
import io.saim.dash.coupon.repository.Issue.IssueRepository;

import io.saim.dash.coupon.util.IssueQueryHelper;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IssueService {

	private final IssueRepository issueRepository;

	public List<Issue> getIssuesByUser(
		DUMMY_ServiceUser user,
		int page, int size,
		String createat_start, String createat_end,
		String business_name, String owner_phone, IssueStatus status
	) {
		BooleanBuilder filterBuilder = IssueQueryHelper.createFilterBuilder(
			createat_start, createat_end,
			business_name, owner_phone, status,
			QIssue.issue
		);

		if(user.isPartner()) {
			assert user instanceof DUMMY_PartnerUser;
			return issueRepository.findIssuesByPartner((DUMMY_PartnerUser)user, filterBuilder, page, size);
		}

		assert user instanceof DUMMY_GeneralUser;
		return issueRepository.findIssuesByVendor((DUMMY_GeneralUser)user, filterBuilder, page, size);
	}

	public Issue getIssue(Long issueId, DUMMY_ServiceUser requestUser) throws ServiceException {
		Issue issue = issueRepository.getById(issueId)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.ISSUE_NOT_FOUND));

		if (requestUser.isPartner()) {
			if (!issue.isRequestedPartner(requestUser))
				throw new ServiceException(ServiceExceptionContent.ISSUE_FORBIDDEN);
		} else {
			if (!issue.getVendorGroup().isMemberIncluded(requestUser))
				throw new ServiceException(ServiceExceptionContent.ISSUE_FORBIDDEN);
		}

		return issue;
	}
}
