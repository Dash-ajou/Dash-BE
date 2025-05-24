package io.saim.dash.account.partner.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class RequestDetailDTO {

	private String requestDetail;
	private Integer requestCount;
	private Integer totalPrice;
	private String approvalDate;

	public RequestDetailDTO(String requestDetail, Integer requestCount, Integer totalPrice, LocalDateTime approvalDate) {
		this.requestDetail = requestDetail;
		this.requestCount = requestCount;
		this.totalPrice = totalPrice;
		this.approvalDate = approvalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}
}
