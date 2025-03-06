package io.saim.dash.security;

import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.repository.SignupNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final SignupNameRepository signupNameRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		GeneralUser user = signupNameRepository.findByGeneralPhone(username)
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return new CustomUserDetails(user);
	}
}
