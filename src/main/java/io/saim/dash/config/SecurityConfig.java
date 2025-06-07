package io.saim.dash.config;

import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import static org.springframework.security.config.Customizer.withDefaults;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.requestMatchers("/", "/public/**", "/auth/google", "/auth/google/callback").permitAll() //OAuth2 관련 허용
				.requestMatchers("/auth/phone/request", "/auth/phone/verify", "/signup/name", "/signup/password", "/signup/complete", "/auth/login","/auth/password-reset/request", "/auth/password-reset/verify", "/auth/password-reset/complete", "/auth/logout", "/general/mypage", "/general/account", "/general/account/phone", "/general/account/email-verify/request", "/general/account/email-verify/confirm", "/general/account/delete", "/signup/partner/details").permitAll()
				.requestMatchers("/signup/unified", "/auth/**","/error", "/css/**", "/js/**", "/images/**").permitAll()

				.anyRequest().authenticated()  //그 외 요청은 인증 필요
			)
			.oauth2Login(oauth2 -> oauth2
				.loginPage("/auth/google") //OAuth 로그인 엔드포인트
				.defaultSuccessUrl("/dashboard", true) //로그인 성공 후 리디렉트
				.failureUrl("/auth/google?error=true") //로그인 실패 시 이동할 경로
			)
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) //세션 유지 설정
			)
			.securityContext(security -> security
				.requireExplicitSave(false) //SecurityContext 유지 활성화
			)
			.logout(logout -> logout
				.logoutSuccessUrl("/") //로그아웃 후 이동할 URL
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
			);
		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("https://dash.milide.net", "http://localhost:5174"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
