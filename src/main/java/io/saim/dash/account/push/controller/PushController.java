package io.saim.dash.account.push.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.common.model.UserType;
import io.saim.dash.account.push.dto.PushBriefDTO;
import io.saim.dash.account.push.dto.PushDTO;
import io.saim.dash.account.push.model.Push;
import io.saim.dash.account.push.service.PushService;
import io.saim.dash.global.dto.PagingResponse;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/push")
@RequiredArgsConstructor
public class PushController {

	private final PushService pushService;

	@GetMapping("/list")
	public PagingResponse<PushBriefDTO> getPushes(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestParam(required = false, defaultValue = "1") int page,
		@RequestParam(required = false, defaultValue = "10") int size,
		@RequestParam(required = false) String received_at_from,
		@RequestParam(required = false) String received_at_to
	) {
		ServiceUser loginUser = getLoginUser(customUserDetails);

		List<Push> pushes = pushService.getPushes(
			loginUser,
			page, size,
			received_at_from, received_at_to
		);

		List<PushBriefDTO> pushResponseList = pushes.stream()
			.map(PushBriefDTO::new)
			.toList();

		return new PagingResponse<>(
			page, size,
			pushResponseList
		);
	}

	private static ServiceUser getLoginUser(CustomUserDetails customUserDetails) {
		ServiceUser user;
		try {
			user = customUserDetails.getGeneralUser();
		} catch (Exception e) {
			user = customUserDetails.getPartnerUser();
		}
		user.setUserType(UserType.valueOf(customUserDetails.getUserType()));
		return user;
	}

}
