package io.saim.dash.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.saim.dash.account.general.resolver.LoginGeneralUserArgumentResolver;
import io.saim.dash.global.interceptor.VersionInterceptor;
import io.saim.dash.security.resolver.LoginPartnerUserArgumentResolver;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new VersionInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/auth/google/callback"); //리디렉션 엔드포인트 제외

	}
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:5174")  // 프론트 로컬 주소
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
			.allowedHeaders("*")
			.allowCredentials(true);
	}

	private final LoginGeneralUserArgumentResolver loginGeneralUserArgumentResolver;
	private final LoginPartnerUserArgumentResolver loginPartnerUserArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(loginGeneralUserArgumentResolver);
		resolvers.add(loginPartnerUserArgumentResolver);
	}
}
