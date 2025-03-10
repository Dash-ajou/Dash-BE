package io.saim.dash.coupon.repository.Issue;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import io.saim.dash.coupon.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.model.Issue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class IssueRepositoryImpl implements IssueRepository {

	private IssueJpaRepository jpaRepository;

	@Override
	public Optional<Issue> getById(long issueId) {
		return jpaRepository.findById(issueId);
	}

	@Override
	public List<Issue> getIssuesByVendor(DUMMY_GeneralUser user) {
		return jpaRepository.findAllByVendors(user.getVendors());
	}

	@Override
	public List<Issue> getIssuesByPartner(DUMMY_PartnerUser user) {
		return jpaRepository.findAllByPartner(user);
	}

}
