package io.saim.dash.coupon.manage.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import io.saim.dash.coupon.common.dto.Coupon.CouponBriefDTO;
import io.saim.dash.coupon.common.dto.Issue.CancelIssueResultDTO;
import io.saim.dash.coupon.common.dto.Issue.CouponIssueLogDTO;
import io.saim.dash.coupon.common.dto.Coupon.RegisteredCouponDTO;
import io.saim.dash.coupon.common.dto.Issue.PauseCouponsResultDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponRegistration;
import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.DUMMY_ServiceUser;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.QIssue;
import io.saim.dash.coupon.common.model.QRequest;
import io.saim.dash.coupon.common.repository.Coupon.CouponRegistrationRepository;
import io.saim.dash.coupon.common.repository.Coupon.CouponRepository;
import io.saim.dash.coupon.common.repository.Issue.IssueRepository;
import io.saim.dash.coupon.common.util.ManageQueryHelper;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManageService {

	private final IssueRepository issueRepository;
	private final CouponRepository couponRepository;
	private final CouponRegistrationRepository couponRegistrationRepository;

	public List<CouponIssueLogDTO> getIssuedRequests(
		DUMMY_ServiceUser user,
		Integer page, Integer size,
		String vendorName, String presidentName, String businessName, Boolean isCompletionInclude
	) {
		BooleanBuilder filterBuilder = ManageQueryHelper.createIssueSearchFilterBuilder(
			vendorName, presidentName, businessName, isCompletionInclude,
			QIssue.issue, QRequest.request
		);

		List<Issue> issues;
		if (user.isPartner()) {
			assert user instanceof DUMMY_PartnerUser;
			issues = issueRepository.findIssuesByPartner(
				(DUMMY_PartnerUser)user, filterBuilder,
				page, size
			);
		} else {
			assert user instanceof DUMMY_GeneralUser;
			issues = issueRepository.findIssuesByVendor(
				(DUMMY_GeneralUser) user, filterBuilder,
				page, size
			);
		}

		return issues.stream()
			.map(CouponIssueLogDTO::new)
			.toList();

	}

	public List<CouponBriefDTO> getCouponsByIssueId(
		DUMMY_ServiceUser user,
		Integer page, Integer size,
		Long issueId
	) {
		getIssue(user, issueId);
		List<Coupon> savedCoupons = couponRepository.findCouponsByIssueId(
			issueId
		);

		return savedCoupons.stream()
			.map(CouponBriefDTO::new)
			.toList();
	}

	public RegisteredCouponDTO getCouponByCouponId(
		DUMMY_ServiceUser user,
		Long issueId, Long couponId
	) {
		if (user.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		Issue issue = getIssue(user, issueId);
		Coupon coupon = couponRepository.findCouponById(couponId);
		CouponRegistration registration = couponRegistrationRepository.findByCouponId(couponId);
		return new RegisteredCouponDTO(issue, coupon, registration);
	}

	@Transactional
	public PauseCouponsResultDTO updateCouponsPauseStatus(DUMMY_ServiceUser user, Long issueId, IssueActiveStatus status) {
		if (user.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		Issue issue = getIssue(user, issueId);

		IssueActiveStatus currentStatus = issue.getIssueActiveStatus();
		if (currentStatus == status)
			throw new ServiceException(ServiceExceptionContent.ACTIVE_STATUS_ALREADY_UPDATED);

		Long updatedCounts = issue.getIssueCnt() - issue.getUsedCnt();
		issue.setIssueActiveStatus(status);
		return new PauseCouponsResultDTO(issue.getIssueCnt(), updatedCounts);
	}

	private Issue getIssue(DUMMY_ServiceUser user, Long issueId) {
		Issue issue = issueRepository.getById(issueId);
		if (issue == null)
			throw new ServiceException(ServiceExceptionContent.ISSUE_NOT_FOUND);
		if (!issue.getRequest().getVendor().isMemberIncluded(user))
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		return issue;
	}

	@Transactional
	public CouponRegistration cancelCouponRegistration(
		DUMMY_ServiceUser user,
		Long issueId, Long couponId
	) {
		if (user.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		getIssue(user, issueId);
		CouponRegistration registration = couponRegistrationRepository.findByCouponId(couponId);
		registration.setIsValid(false);

		return registration;
	}

	public Boolean checkIssueCancellable(DUMMY_ServiceUser user, Long issueId) {
		if (user.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		Issue issue = getIssue(user, issueId);
		if (issue.getIssueActiveStatus() != IssueActiveStatus.DISABLED)
			throw new ServiceException(ServiceExceptionContent.ISSUE_IS_ENABLED);

		return true;
	}

	@Transactional
	public CancelIssueResultDTO cancelIssue(DUMMY_ServiceUser user, Long issueId) {
		if (user.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		Issue issue = getIssue(user, issueId);

		BooleanBuilder couponFilterBuilder = ManageQueryHelper.createCouponSearchFilterBuilder(
			List.of(new CouponStatus[]{CouponStatus.DISABLED}),
			issueId
		);
		Long expiredCnt = couponRepository.cancelCoupons(couponFilterBuilder);

		return new CancelIssueResultDTO(issue, expiredCnt);
	}
}
