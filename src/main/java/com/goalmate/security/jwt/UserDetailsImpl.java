package com.goalmate.security.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class UserDetailsImpl implements UserDetails {
	private final Long id;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;

	@Builder
	public UserDetailsImpl(
		Long id,
		Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.password = null;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getUsername() {
		return id.toString();
	}

	@Override
	public String getPassword() {
		log.warn(">>>>> UserDetailsImpl password has been used!");
		return password;
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

}
