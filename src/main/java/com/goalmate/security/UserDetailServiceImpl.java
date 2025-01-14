package com.goalmate.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.goalmate.domain.Mentee;
import com.goalmate.repository.MenteeRepository;

import lombok.RequiredArgsConstructor;

/*
	OAuth 사용으로 인해 쓰지않음
 */
@Deprecated
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final MenteeRepository menteeRepository;

	@Override
	public UserDetails loadUserByUsername(String socialId) throws UsernameNotFoundException {
		log.info(">>>>>> UserDetailServiceImpl loadUserByUsername");
		Mentee mentee = menteeRepository.findBySocialId(socialId)
			.orElseThrow(() -> new UsernameNotFoundException("User not found with socialId	: " + socialId));

		List<GrantedAuthority> authorities = getAuthorities(mentee);

		return UserDetailsImpl.builder()
			.id(mentee.getId())
			.authorities(authorities)
			.build();
	}

	private List<GrantedAuthority> getAuthorities(Mentee mentee) {
		return mentee.getRole() != null ?
			List.of(new SimpleGrantedAuthority(mentee.getRole().getValue()))
			: List.of();
	}
}
