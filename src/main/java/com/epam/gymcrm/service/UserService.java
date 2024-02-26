package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.exception.UsernameOrPasswordInvalidException;
import com.epam.gymcrm.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public User getUserById(Long userId) {
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
		}
		return null;
	}

	@Transactional
	public Long createUser(String firstName, String lastName) {
		User user = User.builder()
				.firstName(firstName)
				.lastName(lastName)
				.username(generateUsername(firstName, lastName))
				.password(generatePassword())
				.isActive(true)
				.build();
		try {
			userRepository.save(user);
			return user.getId();
		}
		catch (Exception e) {
			log.error("Error while creating user: {}", e.getMessage());
		}
		return null;
	}

	@Transactional
	public Long updateUser(User user) {
		try {
			if(isUsernameAndPasswordValid(user.getId())) {
				User updatedUser = User.builder()
						.id(user.getId())
						.firstName(user.getFirstName())
						.lastName(user.getLastName())
						.username(user.getUsername())
						.password(user.getPassword())
						.isActive(user.getIsActive())
						.build();
				userRepository.save(updatedUser);
				return updatedUser.getId();
			}
			else {
				throw new UsernameOrPasswordInvalidException("Username or password is invalid");
			}
		}
		catch (Exception e) {
			log.error("Error while updating user: {}", e.getMessage());
		}
		return null;
	}

	@Transactional
	public void deleteUser(Long userId) {
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
		}
	}

	private String generateUsername(String firstName, String lastName) {
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

	private String generatePassword() {
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
