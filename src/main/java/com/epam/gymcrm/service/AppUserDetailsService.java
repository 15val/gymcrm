package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.AppUserDetails;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.LoginBlockedException;
import com.epam.gymcrm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
@Slf4j
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private final UserRepository userRepository;
	@Autowired
	private final LoginAttemptService loginAttemptService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (loginAttemptService.isBlocked()) {
			log.error("Security AppUserDetailsService: Login attempt blocked: too many failed attempts");
			throw new LoginBlockedException("blocked");
		}
		try {
			User user = userRepository.findByUsername(username);
			log.info("Security AppUserDetailsService: User successfully found by username: " + username);
			return new AppUserDetails(user);
		}
		catch (UsernameNotFoundException e) {
			log.error("Security AppUserDetailsService: Username was not found: {}", username);
			throw e;
		}
	}
}
