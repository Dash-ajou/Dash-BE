package io.saim.dash.account.partner.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import io.saim.dash.account.partner.dto.PartnerRequestResponseDTO;
import io.saim.dash.account.partner.repository.PartnerRequestRepository;
import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartnerRequestService {

	private final PartnerRequestRepository repository;

	public List<PartnerRequestResponseDTO> searchPartnerRequests(String partnerName, String requestStatus) {
		IssueStatus status = null;

		if (requestStatus != null) {
			try {
				status = IssueStatus.valueOf(requestStatus.toUpperCase());
			} catch (IllegalArgumentException e) {
				status = null;
			}
		}

		if (partnerName == null && status == null) {
			throw new ServiceException(ServiceExceptionContent.INVALID_INPUT);
		}

		return repository.searchPartnerRequests(partnerName, status)
			.stream()
			.map(PartnerRequestResponseDTO::new)
			.collect(Collectors.toList());
	}
}
