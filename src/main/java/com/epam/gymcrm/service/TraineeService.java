package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.repository.TraineeRepository;
import com.epam.gymcrm.repository.UserRepository;
import com.epam.gymcrm.utils.HibernateUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TraineeService {
	private final TraineeRepository traineeRepository;
	private final UserRepository userRepository;
	private final UserService userService;


	public Integer createTrainee(Trainee trainee) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();//annotation custom transactional
		try {
			if (session.get(User.class, trainee.getUser().getId()) == null) {
				throw new UserNotFoundException("User was not found");
			}
			session.persist(trainee);
			transaction.commit();
			return trainee.getId();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public Integer updateTrainee(Trainee trainee) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			Trainee updatedTrainee = Trainee.builder()
					.id(trainee.getId())
					.address(trainee.getAddress())
					.dateOfBirth(trainee.getDateOfBirth())
					.trainerSet(trainee.getTrainerSet())
					.trainingSet(trainee.getTrainingSet())
					.user(trainee.getUser())
					.build();
			session.persist(updatedTrainee);
			transaction.commit();
			return updatedTrainee.getId();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public Integer deleteTrainee(Integer traineeId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Trainee trainee;
		try {
			trainee = session.get(Trainee.class, traineeId);
			if (trainee == null) {
				throw new UserNotFoundException("Trainee was not found");
			} else {
				session.remove(trainee);
				return trainee.getId();
			}
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public Trainee getTraineeById(Integer traineeId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Trainee trainee;
		try {
			trainee = session.get(Trainee.class, traineeId);
			if (trainee == null) {
				throw new UserNotFoundException("Trainee was not found");
			} else {
				return trainee;
			}
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public void switchActive(Integer traineeId){
		User user = userService.getUserById(getTraineeById(traineeId).getUser().getId());//query update user status jpql/native sql
		user.setIsActive(!user.getIsActive());
		userService.updateUser(user);
	}

	public void changePassword(Integer traineeId, String newPassword){
		User user = userService.getUserById(getTraineeById(traineeId).getUser().getId());
		user.setPassword(newPassword);
		userService.updateUser(user);
	}

	public Trainee getTraineeByUsername(String username) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Trainee trainee;
		try {//repository
			trainee = session.get(Trainee.class, userRepository.findByUsername(username).getTrainee().getId());
			if(trainee == null){
				throw new UserNotFoundException("Trainee was not found");
			}
			else {
				return trainee;
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

	public void deleteTraineeByUsername(String username){
		deleteTrainee(getTraineeByUsername(username).getId());
	}
}
