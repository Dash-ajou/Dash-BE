package io.saim.dash.global.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import io.saim.dash.global.dto.CommonResponseAdvice;
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
		String apiVersion = getRequestAPIVersion(request);
		String clientVersion = getClientVersion(request);

		CommonResponseAdvice.VersionResponseDTO versionResponseDTO = new CommonResponseAdvice.VersionResponseDTO(
			apiVersion, clientVersion
		);
		request.setAttribute("version", versionResponseDTO);
		return true;
	}

	private static String getRequestAPIVersion(HttpServletRequest request) throws BadRequestException {
		String[] urlCheck = request.getContextPath().split("/");
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
