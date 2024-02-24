package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.repository.TrainerRepository;
import com.epam.gymcrm.repository.UserRepository;
import com.epam.gymcrm.utils.HibernateUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerService {
	private final TrainerRepository trainerRepository;
	private final UserRepository userRepository;
	private final UserService userService;


	public Integer createTrainer(Trainer trainer) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			if(session.get(User.class, trainer.getUser1().getId()) == null){
				throw new UserNotFoundException("User was not found");
			}
			session.persist(trainer);
			transaction.commit();
			return trainer.getId();
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
	public Integer updateTrainer(Trainer trainer) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			Trainer updatedTrainer = Trainer.builder()
					.id(trainer.getId())
					.user1(trainer.getUser1())
					.trainingSet(trainer.getTrainingSet())
					.traineeSet(trainer.getTraineeSet())
					.trainingType2(trainer.getTrainingType2())
					.build();
			session.persist(updatedTrainer);
			transaction.commit();
			return updatedTrainer.getId();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public Trainer getTrainerById(Integer trainerId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Trainer trainer;
		try {
			trainer = session.get(Trainer.class, trainerId);
			if(trainer == null){
				throw new UserNotFoundException("Trainer was not found");
			}
			else {
				return trainer;
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

	public Integer deleteTrainer(Integer trainerId) {
		throw new UnsupportedOperationException("Not allowed to delete trainer");
	}

	public void switchActive(Integer trainerId) {
		User user = userService.getUserById(getTrainerById(trainerId).getUser1().getId());
		user.setIsActive(!user.getIsActive());
		userService.updateUser(user);
	}

	public void changePassword(Integer trainerId, String newPassword){
		User user = userService.getUserById(getTrainerById(trainerId).getUser1().getId());
		user.setPassword(newPassword);
		userService.updateUser(user);
	}
	public Trainer getTrainerByUsername(String username) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Trainer trainer;
		try {
			trainer = session.get(Trainer.class, userRepository.findByUsername(username).getTrainer().getId());
			if(trainer == null){
				throw new UserNotFoundException("Trainer was not found");
			}
			else {
				return trainer;
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
}
