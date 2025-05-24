package io.saim.dash.security;

import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.partner.model.PartnerUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {
	private final String userType; //GENERAL 또는 PARTNER
	private final String phoneNumber;
	private final String password;
	private final GeneralUser generalUser;
	private final PartnerUser partnerUser;

	//일반 사용자 생성자
	public CustomUserDetails(GeneralUser user) {
		this.userType = "GENERAL";
		this.phoneNumber = user.getPhone();
		this.password = user.getPassword();
		this.generalUser = user;
		this.partnerUser = null;
	}

	//파트너 사용자 생성자
	public CustomUserDetails(PartnerUser user) {
		this.userType = "PARTNER";
		this.phoneNumber = user.getPhone();
		this.password = user.getPassword();
		this.generalUser = null;
		this.partnerUser = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return phoneNumber;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	//일반 사용자 가져오기
	public GeneralUser getGeneralUser() {
		if (!"GENERAL".equals(userType)) {
			throw new IllegalStateException("이 객체는 일반 사용자 정보가 아닙니다.");
		}
		return generalUser;
	}

	//파트너 사용자 가져오기
	public PartnerUser getPartnerUser() {
		if (!"PARTNER".equals(userType)) {
			throw new IllegalStateException("이 객체는 파트너 사용자 정보가 아닙니다.");
		}
		return partnerUser;
	}
}
