package io.saim.dash.account.general.resolver;

import io.saim.dash.account.general.annotation.LoginGeneralUser;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.repository.GeneralUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginGeneralUserArgumentResolver implements HandlerMethodArgumentResolver {

	private final GeneralUserRepository generalUserRepository;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LoginGeneralUser.class)
			&& parameter.getParameterType().equals(GeneralUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, org.springframework.web.bind.support.WebDataBinderFactory binderFactory) {

		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}

		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return null;
		}

		return generalUserRepository.findById(userId).orElse(null);
	}
}
