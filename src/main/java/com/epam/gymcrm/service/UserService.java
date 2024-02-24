package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.repository.UserRepository;
import com.epam.gymcrm.utils.HibernateUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User getUserById(Integer userId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		User user;
		try {
			user = session.get(User.class, userId);
			if(user == null){
				throw new NullPointerException();
			}
			else {
				return user;
			}
		}
		catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return null;
	}

	public Integer createUser(String firstName, String lastName) {
		User user = User.builder()
				.firstName(firstName)
				.lastName(lastName)
				.username(generateUsername(firstName, lastName))
				.password(generatePassword())
				.isActive(true)
				.build();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.persist(user);
			transaction.commit();
			return user.getId();
		}
		catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return null;
	}

	public Integer updateUser(User user) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			User updatedUser = User.builder()
					.id(user.getId())
					.firstName(user.getFirstName())
					.lastName(user.getLastName())
					.username(user.getUsername())
					.password(user.getPassword())
					.isActive(user.getIsActive())
					.build();
			session.persist(updatedUser);
			transaction.commit();
			return updatedUser.getId();
		}
		catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return null;
	}

	public Integer deleteUser(Integer userId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		User user;
		try {
			user = session.get(User.class, userId);
			if (user == null) {
				throw new UserNotFoundException("User was not found");
			} else {
				session.remove(user);
				return user.getId();
			}
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
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
