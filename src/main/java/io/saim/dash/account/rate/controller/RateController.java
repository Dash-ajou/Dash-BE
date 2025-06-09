package io.saim.dash.account.rate.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.common.model.UserType;
import io.saim.dash.account.rate.dto.RegisterRateDTO;
import io.saim.dash.account.rate.service.RateService;
import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rate")
@RequiredArgsConstructor
public class RateController {

	private final RateService rateService;

	@PostMapping("/")
	public void registerRate(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody RegisterRateDTO registerRateDTO
	) {
		ServiceUser loginUser = getLoginUser(customUserDetails);
		rateService.registerRate(loginUser, registerRateDTO.rate());
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
