package io.saim.dash.coupon.common.repository.Manage;

import java.util.List;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.dto.CouponDTO;
import io.saim.dash.coupon.common.dto.CouponIssueLogDTO;
import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.DUMMY_ServiceUser;

public interface ManageRequestRepository {

	List<CouponIssueLogDTO> findIRsByPartner(DUMMY_PartnerUser user, BooleanBuilder filter, Integer page, Integer size);

	List<CouponIssueLogDTO> findIRsByVendor(DUMMY_GeneralUser user, BooleanBuilder filter, Integer page, Integer size);

	List<CouponDTO> findCouponsByIssueId(DUMMY_ServiceUser user, Long issueId, Integer page, Integer size);
}
