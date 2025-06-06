package io.saim.dash.global.resolver;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.saim.dash.global.dto.APIStatus;
import io.saim.dash.global.dto.CommonResponseDTO;
import io.saim.dash.global.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler, Exception ex
	) {
		response.setContentType("application/json;charset=UTF-8");

		try (PrintWriter writer = response.getWriter()) {
			CommonResponseDTO.CommonResponseDTOBuilder<Object> responseDTOBuilder = CommonResponseDTO.builder()
				.apiVersion("unknown")
				.clientVersion("unknown");

			if (ex instanceof ServiceException e) {
				responseDTOBuilder.status(e.getApiStatus()).message(e.getMessage()).data(null);
				response.setStatus(e.getApiStatus().getStatusCode());
			} else {
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

				String errorMessage = ex.getMessage();
				if (errorMessage == null || errorMessage.isEmpty()) errorMessage = "알 수 없는 오류가 발생하였습니다";

				responseDTOBuilder.status(APIStatus.FAILED).message(errorMessage).data(null);
			}

			writer.write(new ObjectMapper().writeValueAsString(responseDTOBuilder.build()));
			writer.flush();

			// 에러 출력
			log.error("[ERROR - GlobalExceptionResolver]", ex);
		} catch (IOException e) {
			// 로깅
		}

		return new ModelAndView(); // handled
	}
}
