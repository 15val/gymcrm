package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.AppUserDetails;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
@Slf4j
public class AppUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = userRepository.findByUsername(username);
			return new AppUserDetails(user);
		}
		catch (UsernameNotFoundException e) {
			log.error("Security AppUserDetailsService: Username was not found: {}", username);
			throw e;
		}
	}
}
