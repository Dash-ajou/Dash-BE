package io.saim.dash.account.partner.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MenuUsageStatsDTO {
	private String menuName;
	private Long menuIssued;
	private Long menuUsed;
	private double menuUsageRate;
}
