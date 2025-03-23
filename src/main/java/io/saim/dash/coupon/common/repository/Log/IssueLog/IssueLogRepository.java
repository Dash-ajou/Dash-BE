package io.saim.dash.coupon.common.repository.Log.IssueLog;

import org.springframework.data.jpa.repository.JpaRepository;

import io.saim.dash.coupon.common.model.IssueLog;

public interface IssueLogRepository {

	void save(IssueLog issueLog);
}

interface IssueLogJPARepository extends JpaRepository<IssueLog, Long> {}
