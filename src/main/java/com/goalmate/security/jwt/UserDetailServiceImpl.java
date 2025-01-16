package com.goalmate.security.jwt;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.goalmate.domain.mentee.MenteeEntity;
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
		MenteeEntity menteeEntity = menteeRepository.findBySocialId(socialId)
			.orElseThrow(() -> new UsernameNotFoundException("User not found with socialId	: " + socialId));

		List<GrantedAuthority> authorities = getAuthorities(menteeEntity);

		return UserDetailsImpl.builder()
			.id(menteeEntity.getId())
			.authorities(authorities)
			.build();
	}

	private List<GrantedAuthority> getAuthorities(MenteeEntity menteeEntity) {
		return menteeEntity.getRole() != null ?
			List.of(new SimpleGrantedAuthority(menteeEntity.getRole().getValue()))
			: List.of();
	}
}
