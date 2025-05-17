package io.saim.dash.account.general.coupon.service;

import io.saim.dash.account.general.coupon.repository.CouponRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponDeleteService {

	private final CouponRepository couponRepository;

	@Transactional
	public void deleteCoupon(Long couponId) {
		boolean exists = couponRepository.existsById(couponId);

		if (!exists) {
			throw new ServiceException(ServiceExceptionContent.COUPON_NOT_FOUND);
		}

		couponRepository.deleteById(couponId);
	}
}
