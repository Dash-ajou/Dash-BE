package io.saim.dash.coupon.issue.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.saim.dash.coupon.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.model.DUMMY_ServiceUser;
import io.saim.dash.coupon.model.Issue;
import io.saim.dash.coupon.repository.Issue.IssueRepository;

import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IssueService {

	private final IssueRepository issueRepository;

	public List<Issue> getIssuesByUser(DUMMY_ServiceUser user) {
		if(user.isPartner())
			return issueRepository.getIssuesByPartner((DUMMY_PartnerUser)user);

		return issueRepository.getIssuesByVendor((DUMMY_GeneralUser)user);
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
