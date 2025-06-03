package io.saim.dash.account.push.service;

import java.time.LocalDateTime;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.repository.GeneralUserRepository;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import io.saim.dash.account.push.dto.UserSearchResultDTO;
import io.saim.dash.account.push.model.Push;
import io.saim.dash.account.push.repository.PushRepository;
import io.saim.dash.account.push.util.PushQueryHelper;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PushService {

	private final PushRepository pushRepository;
	private final PartnerUserRepository partnerUserRepository;
	private final GeneralUserRepository generalUserRepository;

	public List<Push> getPushes(
		ServiceUser loginUser,
		int page, int size,
		String receivedAtFrom, String receivedAtTo
	) {
		UserSearchResultDTO userSearchResultDTO = searchUser(loginUser);

		BooleanBuilder filter = PushQueryHelper.createFilterBuilder(
			userSearchResultDTO,
			receivedAtFrom, receivedAtTo
		);
		return pushRepository.findByFilter(filter, page, size);
	}

	@NotNull
	private UserSearchResultDTO searchUser(ServiceUser loginUser) {
		PartnerUser partnerUser = null;
		GeneralUser generalUser = null;

		if (loginUser.isPartner())
			partnerUser = partnerUserRepository.findById(((PartnerUser)loginUser).getId())
				.orElse(null);
		else
			generalUser = generalUserRepository.findById(((GeneralUser)loginUser).getId())
				.orElse(null);

		return new UserSearchResultDTO(partnerUser, generalUser);
	}
}
