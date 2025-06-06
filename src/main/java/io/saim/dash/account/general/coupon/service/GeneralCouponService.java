package io.saim.dash.account.general.coupon.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.saim.dash.account.general.coupon.repository.CouponPaymentLogRepository;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.coupon.dto.UsedCouponResponseDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponPaymentLog;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeneralCouponService {

	private final CouponPaymentLogRepository couponPaymentLogRepository;

	@Transactional(readOnly = true)
	public List<UsedCouponResponseDTO> getUsedCoupons(GeneralUser user) {
		List<CouponPaymentLog> logs = couponPaymentLogRepository.findAllByUser(user);

		return logs.stream().map(log -> {
			Coupon coupon = log.getPaymentCode().getCoupon();
			return UsedCouponResponseDTO.builder()
				.couponId(coupon.getCouponId())
				.paymentCode(log.getPaymentCode().getPaymentCode())
				.partnerName(coupon.getIssue().getPartner().getPartnerName())
				.usedAt(log.getUsedAt())
				.build();
		}).collect(Collectors.toList());
	}
}
