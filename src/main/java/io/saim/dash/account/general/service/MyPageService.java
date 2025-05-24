package io.saim.dash.account.general.service;

import io.saim.dash.account.general.dto.MyPageResponseDTO;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.repository.GeneralUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

	private final GeneralUserRepository signupNameRepository;

	public MyPageResponseDTO getMyPageInfo(GeneralUser user) {
		if (user == null) {
			throw new IllegalArgumentException("유효하지 않은 사용자입니다.");
		}

		return new MyPageResponseDTO(
			"SUCCESS",
			"계정 정보를 성공적으로 가져왔습니다.",
			new MyPageResponseDTO.Data(
				user.getName(),
				new MyPageResponseDTO.CouponStatus(1, 0), //예제 값, DB에서 가져오기
				new MyPageResponseDTO.Menus()
			)
		);
	}
}
