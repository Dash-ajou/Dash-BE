package io.saim.dash.global.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.exception.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class VersionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
	) throws Exception {
		// String apiVersion = getRequestAPIVersion(request);
		// String clientVersion = getClientVersion(request);
		String apiVersion = "1.0"; // TODO: 서버 버전라우팅 이전까지 임시고정
		String clientVersion = "1.0"; // TODO: 클라이언트 버전 임시고정

		CommonResponseDTO.VersionResponseDTO versionResponseDTO = new CommonResponseDTO.VersionResponseDTO(
			apiVersion, clientVersion
		);
		request.setAttribute("version", versionResponseDTO);
		return true;
	}

	private static String getRequestAPIVersion(HttpServletRequest request) throws BadRequestException {
		String[] urlCheck = request.getRequestURI().split("/");
		if (urlCheck.length < 2) {
			throw new BadRequestException();
		}

		return urlCheck[1];
	}

	private String getClientVersion(HttpServletRequest request) throws BadRequestException {
		String clientVersion = request.getHeader("Client-Version");
		if (clientVersion == null) {
			throw new BadRequestException();
		}

		return clientVersion;
	}
}
