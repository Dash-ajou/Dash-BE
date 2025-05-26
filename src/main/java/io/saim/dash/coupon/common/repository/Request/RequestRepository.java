package io.saim.dash.coupon.common.repository.Request;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.coupon.common.model.Request;

public interface RequestRepository {

	Optional<Request> getById(long issueId);

	// List<Issue> getIssuesByVendor(DUMMY_GeneralUser user);
	List<Request> findRequestsByVendor(GeneralUser user, BooleanBuilder filterBuilder, int page, int size);
	// List<Issue> getIssuesByPartner(DUMMY_PartnerUser user);
	List<Request> findRequestsByPartner(PartnerUser user, BooleanBuilder filterBuilder, int page, int size);

	void save(Request request);

	void delete(Request request);

	void flush();

	// List<Issue> getIssueByUserId(Long userId);

	Request getReferenceById(Long requestId);
}
