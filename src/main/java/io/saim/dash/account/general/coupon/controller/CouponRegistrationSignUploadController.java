package io.saim.dash.account.general.coupon.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.common.model.UserType;
import io.saim.dash.account.general.coupon.dto.UploadSignImage;
import io.saim.dash.account.general.coupon.service.CouponRegistrationSignUploadService;

import io.saim.dash.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/general/coupons")
@RequiredArgsConstructor
public class CouponRegistrationSignUploadController {
	private final CouponRegistrationSignUploadService couponRegistrationSignUploadService;

	@PostMapping("/register/sign")
	public void uploadSignImage(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@ModelAttribute UploadSignImage uploadSignImage
	) {
		ServiceUser loginUser = getLoginUser(userDetails);
		couponRegistrationSignUploadService.saveSign(
			loginUser,
			uploadSignImage.sign(), uploadSignImage.registerCode()
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
