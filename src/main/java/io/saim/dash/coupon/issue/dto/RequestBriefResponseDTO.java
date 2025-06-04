package io.saim.dash.coupon.issue.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.dto.PartnerDTO;
import io.saim.dash.coupon.common.dto.Product.ProductDTO;
import io.saim.dash.coupon.common.dto.Product.RequestProductBriefDTO;
import io.saim.dash.coupon.common.dto.Product.RequestProductDTO;
import io.saim.dash.coupon.common.dto.VendorDTO;
import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.model.Vendor;
import io.saim.dash.coupon.payment.dto.ProductBriefDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class RequestBriefResponseDTO {
	private Long request_id;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime created_at;

	private VendorDTO vendor;
	private PartnerDTO partner;
	private IssueStatus status;

	private List<RequestProductBriefDTO> products;

	public RequestBriefResponseDTO(Request request, Boolean isPartnerResponse) {
		this.partner = new PartnerDTO(request.getPartner());

		this.request_id = request.getRequestId();
		this.created_at = request.getCreatedAt();
		this.vendor = new VendorDTO(request.getVendor());
		this.status = request.getStatus();

		this.products = request.getRequestProducts().stream()
			.map(RequestProductBriefDTO::new)
			.toList();
	}
}
