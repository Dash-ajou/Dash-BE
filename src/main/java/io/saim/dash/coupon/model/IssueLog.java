package io.saim.dash.coupon.model;

import java.time.LocalDateTime;

import io.saim.dash.coupon.common.constant.ActiveStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class IssueLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private ActiveStatus activeStatus;

	@OneToOne
	private Issue issue;
	private Long issueCnt;

	@Nullable
	private LocalDateTime paidAt;
	private LocalDateTime confirmedAt;
	private Long paidPrice;
}
