package io.saim.dash.coupon.manage.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.dto.CouponIssueLogDTO;
import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.DUMMY_ServiceUser;
import io.saim.dash.coupon.common.model.QIssueLog;
import io.saim.dash.coupon.common.model.QIssueRequest;
import io.saim.dash.coupon.common.repository.Manage.ManageRequestRepository;
import io.saim.dash.coupon.common.util.ManageQueryHelper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManageService {

	private final ManageRequestRepository manageRequestRepository;

	public List<CouponIssueLogDTO> getIssuedIRs(
		DUMMY_ServiceUser user,
		int page, int size,
		String vendorName, String presidentName, String businessName, boolean isCompletionInclude
	) {
		BooleanBuilder filterBuilder = ManageQueryHelper.createFilterBuilder(
			vendorName, presidentName, businessName, isCompletionInclude,
			QIssueLog.issueLog, QIssueRequest.issueRequest
		);

		if (user.isPartner()) {
			assert user instanceof DUMMY_PartnerUser;
			return manageRequestRepository.findIRsByPartner(
				(DUMMY_PartnerUser)user, filterBuilder,
				page, size
			);
		}

		assert user instanceof DUMMY_GeneralUser;
		return manageRequestRepository.findIRsByVendor(
			(DUMMY_GeneralUser)user, filterBuilder,
			page, size
		);
	}

}
