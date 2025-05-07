package io.saim.dash.coupon.common.repository.Issue;

import org.springframework.stereotype.Repository;

import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.repository.jpa.IssueJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class IssueRepositoryImpl implements IssueRepository {

	private final IssueJpaRepository issueJpaRepository;

	@Override
	public void save(Issue issue) {
		issueJpaRepository.save(issue);
	}
}
