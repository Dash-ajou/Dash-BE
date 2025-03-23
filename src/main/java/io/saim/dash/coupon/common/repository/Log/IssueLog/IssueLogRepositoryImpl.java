package io.saim.dash.coupon.common.repository.Log.IssueLog;

import org.springframework.stereotype.Repository;

import io.saim.dash.coupon.common.model.IssueLog;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class IssueLogRepositoryImpl implements IssueLogRepository {

	private final IssueLogJPARepository issueLogJPARepository;

	@Override
	public void save(IssueLog issueLog) {
		issueLogJPARepository.save(issueLog);
	}
}
