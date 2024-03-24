package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.exception.UsernameOrPasswordInvalidException;
import com.epam.gymcrm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	@Autowired
	private final UserRepository userRepository;
	@Autowired
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public User getUserById(Long userId) throws UserNotFoundException, UsernameOrPasswordInvalidException {
		try {
			if(isUsernameAndPasswordValid(userId)) {
				User user = userRepository.findById(userId).orElse(null);
				if (user == null) {
					throw new UserNotFoundException("User was not found");
				} else {
					return user;
				}
			}
			else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		}
		catch (Exception e) {
			log.error("Error while retrieving user: {}", e.getMessage());
			throw e;
		}
	}

	@Transactional
	public Long createUser(String firstName, String lastName) {
		String password = generatePassword();
		String encodedPassword = passwordEncoder.encode(password);
		User user = User.builder()
				.firstName(firstName)
				.lastName(lastName)
				.username(generateUsername(firstName, lastName))
				.password(encodedPassword)
				.isActive(true)
				.build();
		try {
			userRepository.save(user);
			return userRepository.findByUsername(user.getUsername()).getId();
		}
		catch (Exception e) {
			log.error("Error while creating user: {}", e.getMessage());
			throw e;
		}
	}

	@Transactional
	public Long updateUser(User user) throws UsernameOrPasswordInvalidException {
		try {
			if(isUsernameAndPasswordValid(user.getId())) {
				User existingUser = userRepository.findById(user.getId()).orElse(null);
				if(existingUser != null && !Objects.equals(user.getUsername(), existingUser.getUsername())){
					throw new UnsupportedOperationException("Username cannot be changed");
				}
				String password = user.getPassword();
				String encodedPassword = passwordEncoder.encode(password);
				user.setPassword(encodedPassword);
				userRepository.save(user);
				return user.getId();
			}
			else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		}
		catch (Exception e) {
			log.error("Error while updating user: {}", e.getMessage());
			throw e;
		}
	}

	@Transactional
	public void deleteUser(Long userId) throws UserNotFoundException, UsernameOrPasswordInvalidException {
		try {
			if(isUsernameAndPasswordValid(userId)) {
				User user = userRepository.findById(userId).orElse(null);
				if (user == null) {
					throw new UserNotFoundException("User was not found");
				} else {
					userRepository.delete(user);
				}
			}
			else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		} catch (Exception e) {
			log.error("Error while deleting user: {}", e.getMessage());
			throw e;
		}
	}

	public String generateUsername(String firstName, String lastName) {
		if(userRepository == null) {
			return  firstName + "." + lastName;
		}
		Predicate<String> usernameExists = userRepository::existsByUsername;
		String username = firstName + "." + lastName;
		int serialNumber = 1;
		String finalUsername = username;
		while (usernameExists.test(finalUsername)) {
			finalUsername = username + serialNumber;
			serialNumber++;
		}
		return finalUsername;
	}

	public String generatePassword() {
		String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuilder password = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			int index = random.nextInt(symbols.length());
			password.append(symbols.charAt(index));
		}
		return password.toString();
	}

	@Transactional
	public boolean isUsernameAndPasswordValid(Long userId){
		try{
			User user = userRepository.findById(userId).orElse(null);
			if(user != null && user.getUsername() != null && user.getPassword() != null) {
				return true;
			}
		} catch (Exception e) {
			log.error("Error while retrieving user during validation: {}", e.getMessage());
		}
		return false;
	}
}
