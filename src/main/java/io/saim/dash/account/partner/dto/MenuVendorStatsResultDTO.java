package io.saim.dash.account.partner.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuVendorStatsResultDTO {
	private String menu_name;
	private int menu_vendors;
	private List<MenuVendorStatsResponseDTO> vendors;
}
