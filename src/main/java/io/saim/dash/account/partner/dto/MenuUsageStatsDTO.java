package io.saim.dash.account.partner.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuUsageStatsDTO {
	private String menuName;
	private int menuIssued;
	private int menuUsed;
	private double menuUsageRate;
}
