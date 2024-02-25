package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public User getUserById(Long userId) {
		try {
			User user = userRepository.findById(userId).orElse(null);
			if(user == null){
				throw new UserNotFoundException("User was not found");
			}
			else {
				return user;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public Long updateUser(User user) {
		try {
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
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public void deleteUser(Long userId) {
		try {
			User user = userRepository.findById(userId).orElse(null);
			if (user == null) {
				throw new UserNotFoundException("User was not found");
			} else {
				userRepository.delete(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
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

}
