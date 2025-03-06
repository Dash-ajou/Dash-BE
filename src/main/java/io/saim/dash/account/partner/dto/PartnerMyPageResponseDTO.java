package io.saim.dash.account.partner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PartnerMyPageResponseDTO {
	private String ownerName;
	private List<MenuSection> menus;

	@Getter
	@AllArgsConstructor
	public static class MenuSection {
		private String section;
		private List<MenuItem> items;
	}

	@Getter
	@AllArgsConstructor
	public static class MenuItem {
		private String title;
		private String url;
	}
}
