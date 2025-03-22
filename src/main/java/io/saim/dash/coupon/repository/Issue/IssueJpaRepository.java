package io.saim.dash.coupon.repository.Issue;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.saim.dash.coupon.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.model.Issue;
import io.saim.dash.coupon.model.VendorGroup;

public interface IssueJpaRepository extends JpaRepository<Issue, Long> {
}
