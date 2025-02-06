package io.saim.dash.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/public/**", "/auth/google", "/auth/google/callback").permitAll() //OAuth2 관련 허용
				.requestMatchers("/auth/phone/request", "/auth/phone/verify", "/signup/name", "/signup/password", "/signup/complete", "/auth/login","/auth/password-reset/request", "/auth/password-reset/verify", "/auth/password-reset/complete", "/auth/logout", "/general/mypage", "/general/account", "/general/account/phone", "/general/account/email-verify/request", "/general/account/email-verify/confirm").permitAll()
				.anyRequest().authenticated()  //그 외 요청은 인증 필요
			)
			.oauth2Login(oauth2 -> oauth2
				.loginPage("/auth/google") //OAuth 로그인 엔드포인트
				.defaultSuccessUrl("/dashboard", true) //로그인 성공 후 리디렉트
				.failureUrl("/auth/google?error=true") //로그인 실패 시 이동할 경로
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
}
