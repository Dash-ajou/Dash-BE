package io.saim.dash.account.general.service;

import io.saim.dash.account.general.dto.MyPageResponseDTO;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.repository.GeneralUserRepository;
import io.saim.dash.account.general.coupon.service.CouponStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MyPageService {

	private final GeneralUserRepository signupNameRepository;
	private final CouponStatusService couponStatusService;

	public MyPageResponseDTO getMyPageInfo(GeneralUser user) {
		if (user == null) {
			throw new IllegalArgumentException("유효하지 않은 사용자입니다.");
		}

		Map<String, Integer> couponStatus = couponStatusService.getCouponStatus(user.getId());

		return new MyPageResponseDTO(
			"SUCCESS",
			"계정 정보를 성공적으로 가져왔습니다.",
			new MyPageResponseDTO.Data(
				user.getName(),
				new MyPageResponseDTO.CouponStatus(
					couponStatus.getOrDefault("usable_count", 0),
					couponStatus.getOrDefault("used_count", 0)
				),
				new MyPageResponseDTO.Menus()
			)
		);
	}
}
