package io.saim.dash.account.partner.service;

import io.saim.dash.account.partner.dto.PartnerMyPageResponseDTO;
import io.saim.dash.account.partner.dto.PartnerMyPageResponseDTO.MenuSection;
import io.saim.dash.account.partner.dto.PartnerMyPageResponseDTO.MenuItem;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerMyPageService {

	private final PartnerUserRepository partnerRepository;

	public PartnerMyPageResponseDTO getPartnerMyPage(PartnerUser partnerUser) {
		String ownerName = partnerUser.getOwnerName();

		List<MenuSection> menus = Arrays.asList(
			new MenuSection("내 정보", Arrays.asList(
				new MenuItem("계정 정보", "/partner/mypage/account"),
				new MenuItem("비밀번호 변경하기", "/auth/password-reset/request")
			)),
			new MenuSection("고객센터", Arrays.asList(
				new MenuItem("공지사항", "/support/notice"),
				new MenuItem("FAQ", "/support/faq"),
				new MenuItem("문의하기", "/support/contact")
			))
		);

		return new PartnerMyPageResponseDTO(ownerName, menus);
	}
}
