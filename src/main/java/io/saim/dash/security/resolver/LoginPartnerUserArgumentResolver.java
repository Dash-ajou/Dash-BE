package io.saim.dash.security.resolver;

import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginPartnerUserArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(PartnerUser.class)
			&& parameter.getParameterName().equals("loginPartnerUser");
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		org.springframework.web.bind.support.WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		Object user = request.getSession().getAttribute("partner_user");

		if (user == null || !(user instanceof PartnerUser)) {
			throw new ServiceException(ServiceExceptionContent.UNAUTHORIZED_PARTNER_LOGIN);
		}

		return user;
	}
}
