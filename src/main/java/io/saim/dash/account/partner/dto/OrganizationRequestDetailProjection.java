package io.saim.dash.account.partner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrganizationRequestDetailProjection {
	private final String productName;
	private final int requestCount;
	private final Long price;
	private final LocalDateTime approvalDate;

	public OrganizationRequestDetailProjection(String productName, Long quantity, Long price, LocalDateTime approvalDate) {
		this.productName = productName;
		this.requestCount = quantity.intValue();
		this.price = price;
		this.approvalDate = approvalDate;
	}

	public Long getTotalPrice() {
		return price * requestCount;
	}

	public String getTotalPriceFormatted() {
		return String.format("%,d원", getTotalPrice());
	}
}
