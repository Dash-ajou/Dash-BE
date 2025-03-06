package io.saim.dash.security;

import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.account.general.repository.SignupNameRepository;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final SignupNameRepository signupNameRepository;
	private final PartnerUserRepository partnerUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//일반 사용자(GeneralUser) 조회
		GeneralUser generalUser = signupNameRepository.findByGeneralPhone(username)
			.orElse(null);
		if (generalUser != null) {
			return new CustomUserDetails(generalUser);
		}

		//파트너 사용자(PartnerUser) 조회
		PartnerUser partnerUser = partnerUserRepository.findByOwnerPhone(username)
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return new CustomUserDetails(partnerUser);
	}
}
