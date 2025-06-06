package io.saim.dash.account.partner.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuVendorStatsResultDTO {

	@JsonProperty("menu_name")
	private String menu_name;

	@JsonProperty("menu_vendors")
	private int menu_vendors;
	private List<MenuVendorStatsResponseDTO> vendors;
}
