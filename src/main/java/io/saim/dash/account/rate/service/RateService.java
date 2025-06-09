package io.saim.dash.account.rate.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.repository.GeneralUserRepository;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RateService {

	private final GeneralUserRepository generalUserRepository;

	public void registerRate(ServiceUser loginUser, Float rate) {
		GeneralUser user = getGeneralAPIAccessUser(loginUser);

		rate = adjustRate(rate);
		user.setRate(rate);
	}

	private Float adjustRate(Float rate) {
		if (rate > 5) return 5.0f;
		if (rate < 0) return 0f;
		return rate;
	}

	private GeneralUser getGeneralAPIAccessUser(ServiceUser serviceUser) {
		if (serviceUser.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		return generalUserRepository.findById(serviceUser.getId())
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.USER_NOT_FOUND));
	}
}
