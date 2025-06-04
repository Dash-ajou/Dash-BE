package io.saim.dash.account.partner.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuUsageStatsDTO {
	private String menuName;
	private Long menuIssued;
	private Long menuUsed;
	private double menuUsageRate;

	public MenuUsageStatsDTO(String menuName, Long menuIssued, Long menuUsed, Double menuUsageRate) {
		this.menuName = menuName;
		this.menuIssued = menuIssued;
		this.menuUsed = menuUsed;
		this.menuUsageRate = menuUsageRate != null ? menuUsageRate : 0.0;
	}
}

