package io.saim.dash.coupon.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.dto.CouponIssueLogDTO;
import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.DUMMY_ServiceUser;
import io.saim.dash.coupon.common.model.IssueLog;
import io.saim.dash.coupon.common.model.QIssueLog;
import io.saim.dash.coupon.common.model.QIssueRequest;
import io.saim.dash.coupon.common.repository.Manage.ManageRequestRepository;
import io.saim.dash.coupon.common.util.ManageQueryHelper;
import io.saim.dash.coupon.manage.dto.MFindRequestDTO;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManageService {

	private final ManageRequestRepository manageRequestRepository;

	public List<CouponIssueLogDTO> getIssuedRequests(DUMMY_ServiceUser user, MFindRequestDTO request) {
		BooleanBuilder filterBuilder = ManageQueryHelper.createFilterBuilder(
			request.getVendorName(),
			request.getPresidentName(), request.getBusinessName(),
			request.getIncludeCompleted(),
			QIssueLog.issueLog, QIssueRequest.issueRequest
		);

		if (user.isPartner()) {
			assert user instanceof DUMMY_PartnerUser;
			return manageRequestRepository.findIssuedRequestsByPartner(
				(DUMMY_PartnerUser)user, filterBuilder,
				request.getPage(), request.getSize()
			);
		}

		assert user instanceof DUMMY_GeneralUser;
		return manageRequestRepository.findIssuedRequestsByVendor(
			(DUMMY_GeneralUser)user, filterBuilder,
			request.getPage(), request.getSize()
		);
	}

}
