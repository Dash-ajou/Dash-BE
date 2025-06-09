package io.saim.dash.account.partner.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.saim.dash.account.general.coupon.dto.UsedCouponResponseDTO;
import io.saim.dash.account.general.coupon.repository.CouponPaymentLogRepository;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponPaymentLog;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartnerCouponService {

	private final CouponPaymentLogRepository couponPaymentLogRepository;

	@Transactional(readOnly = true)
	public List<UsedCouponResponseDTO> getUsedCouponsByPartner(Long partnerId) {
		List<CouponPaymentLog> logs =
			couponPaymentLogRepository.findUsedLogsByPartnerId(partnerId, CouponStatus.USED);

		return logs.stream().map(log -> {
			Coupon coupon = log.getPaymentCode().getCoupon();
			return UsedCouponResponseDTO.builder()
				.couponId(coupon.getCouponId())
				.couponName(coupon.getProduct().getProductName())
				.paymentCode(log.getPaymentCode().getPaymentCode())
				.paymentId(log.getPaymentId())
				.partnerName(coupon.getIssue().getPartner().getPartnerName())
				.usedAt(log.getUsedAt())
				.build();
		}).collect(Collectors.toList());
	}
}

