package io.saim.dash.coupon.manage.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.dto.Coupon.CouponBriefDTO;
import io.saim.dash.coupon.common.dto.Issue.CouponIssueLogDTO;
import io.saim.dash.coupon.common.dto.Coupon.RegisteredCouponDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.DUMMY_ServiceUser;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.QIssue;
import io.saim.dash.coupon.common.model.QRequest;
import io.saim.dash.coupon.common.repository.Coupon.CouponRepository;
import io.saim.dash.coupon.common.repository.Issue.IssueRepository;
import io.saim.dash.coupon.common.repository.Request.RequestRepository;
import io.saim.dash.coupon.common.util.ManageQueryHelper;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManageService {

	private final RequestRepository requestRepository;
	private final IssueRepository issueRepository;
	private final CouponRepository couponRepository;

	public List<CouponIssueLogDTO> getIssuedRequests(
		DUMMY_ServiceUser user,
		Integer page, Integer size,
		String vendorName, String presidentName, String businessName, Boolean isCompletionInclude
	) {
		BooleanBuilder filterBuilder = ManageQueryHelper.createFilterBuilder(
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
		// checkUserPrivilege(user, issueId);
		List<Coupon> savedCoupons = couponRepository.findCouponsByIssueId(
			issueId
		);

		return savedCoupons.stream()
			.map(CouponBriefDTO::new)
			.toList();
	}

	private void checkUserPrivilege(DUMMY_ServiceUser user, Long issueId) {
		// Issue issue = issueRepository.getById(issueId);
		// if (
		// 	(!issue.getRequest())
		// )
	}

	public RegisteredCouponDTO getCouponByCouponId(
		DUMMY_ServiceUser user,
		Long issueId, Long couponId
	) {
		if (user.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		Coupon coupon = couponRepository.findCouponById(couponId);
		return new RegisteredCouponDTO(coupon);
	}


}
