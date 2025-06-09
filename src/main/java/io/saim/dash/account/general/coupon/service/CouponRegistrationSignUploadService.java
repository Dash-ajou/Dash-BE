package io.saim.dash.account.general.coupon.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.repository.GeneralUserRepository;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.repository.Coupon.CouponRepository;
import io.saim.dash.coupon.common.util.ImageUtil;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponRegistrationSignUploadService {

	private final CouponRepository couponRepository;
	private final GeneralUserRepository generalUserRepository;

	@Transactional
	public void saveSign(ServiceUser serviceUser, MultipartFile signFile, String couponRegistrationNumber) {
		GeneralUser user = getGeneralAPIAccessUser(serviceUser);
		Coupon coupon = getCouponByRegistrationNumber(user, couponRegistrationNumber);

		String signFileName = createSignImageFileName(user, coupon);
		String savedKey = saveSignImage(signFileName, signFile);
		coupon.setReceiptSignImg(savedKey);
	}

	private Coupon getCouponByRegistrationNumber(GeneralUser user, String couponRegistrationNumber) {
		Coupon coupon = couponRepository.findByRegistrationCode(couponRegistrationNumber);
		Request request = coupon.getIssue().getRequest();

		if (!request.getVendor().isMemberIncluded(user))
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		return coupon;
	}

	private GeneralUser getGeneralAPIAccessUser(ServiceUser serviceUser) {
		if (serviceUser.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		return generalUserRepository.findById(serviceUser.getId())
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.USER_NOT_FOUND));
	}

	private String createSignImageFileName(GeneralUser user, Coupon coupon) {
		return user.getId().toString() + "_" + coupon.getCouponId() + "_sign.png";
	}

	private String saveSignImage(String filename, MultipartFile scanImage) {
		try {
			return ImageUtil.saveImage(ImageUtil.AccessType.SIGN, scanImage, filename);
		} catch (IOException e) {
			throw new ServiceException(ServiceExceptionContent.FILE_SAVE_ERROR);
		}
	}
}
