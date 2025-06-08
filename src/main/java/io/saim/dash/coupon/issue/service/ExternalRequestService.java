package io.saim.dash.coupon.issue.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExternalRequestService {
	@Async
	public void requestCouponImageCreate(Long requestId) {
		String url = System.getenv("COUPON_IMAGE_CREATE_URL");

		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			Map<String, Object> body = new HashMap<>();
			body.put("request_id", requestId);

			HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
			restTemplate.postForEntity(url, request, Void.class);
		} catch (Exception e) {
			log.error("Failed to request coupon image creation: {}", e.getMessage());
		}
	}
}
