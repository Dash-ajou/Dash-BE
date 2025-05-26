package io.saim.dash.coupon.issue.dto;

import java.util.List;

import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.dto.Product.RequestProductDTO;
import io.saim.dash.coupon.common.dto.PartnerDTO;
import io.saim.dash.coupon.common.dto.VendorDTO;
import io.saim.dash.coupon.common.model.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor
@Builder
public class RequestSpecResponseDTO {
	private Long request_id;
	private String created_at;
	private IssueStatus status;
	private VendorDTO vendor;
	private PartnerDTO partner;
	private List<RequestProductDTO> products;

	public RequestSpecResponseDTO(Request request, Boolean isPartner) {
		this.request_id = request.getRequestId();
		this.created_at = request.getCreatedAt().toString();
		this.status = request.getStatus();
		this.vendor = new VendorDTO(request.getVendor());
		this.partner = new PartnerDTO(request.getPartner());
		this.products = request.getRequestProducts().stream()
			.map(v -> RequestProductDTO.builder()
				.productId(v.getProduct().getProductId())
				.productName(v.getProduct().getProductName())
				.count(v.getQuantity())
				.build()
			)
			.toList();
	}
}
