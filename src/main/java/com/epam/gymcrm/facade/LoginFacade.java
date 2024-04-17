package com.epam.gymcrm.facade;

import com.epam.gymcrm.dto.UpdatePasswordDto;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.exception.UsernameOrPasswordInvalidException;
import com.epam.gymcrm.repository.UserRepository;
import com.epam.gymcrm.service.UserService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@Service
@RequiredArgsConstructor
@Slf4j
public class LoginFacade {

	private final UserService userService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	@Transactional
	public void updatePassword(UpdatePasswordDto request) throws UserNotFoundException, UsernameOrPasswordInvalidException {
		try {
			String username = request.getUsername();
			String password = passwordEncoder.encode(request.getPassword());
			String newPassword = passwordEncoder.encode(request.getNewPassword());
			User user = userRepository.findByUsername(username);
			if(user == null || !Objects.equals(user.getPassword(), password)){
				throw new UserNotFoundException("Username or password is incorrect");
			}

			user.setPassword(newPassword);
			userService.updateUser(user);
		}
		catch (Exception e) {
			log.error("Facade: Error while updating password: {}", e.getMessage());
			throw e;
		}
	}

}
