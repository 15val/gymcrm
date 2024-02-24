package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.Trainer;
import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.entity.TrainingType;
import com.epam.gymcrm.entity.User;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.repository.TrainingRepository;
import com.epam.gymcrm.utils.HibernateUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingService {
	private TrainingRepository trainingRepository;


	public Integer createTraining(Training training) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			if(session.get(User.class, training.getTrainee1().getUser().getId()) == null || session.get(User.class, training.getTrainer1().getUser1().getId()) == null){
				throw new UserNotFoundException("User was not found");
			}
			else if(session.get(TrainingType.class, training.getTrainingType1().getId()) == null){
				throw new UserNotFoundException("Training type was not found");
			}
			session.persist(training);
			transaction.commit();
			return training.getId();
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

	public Training getTrainingById(Integer trainingId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Training training;
		try {
			training = session.get(Training.class, trainingId);
			if(training == null){
				throw new UserNotFoundException("Training was not found");
			}
			else {
				return training;
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

	public Integer deleteTraining(Integer trainingId) {
		throw new UnsupportedOperationException("Not allowed to delete training");
	}
	public Training updateTraining(Training training) {
		throw new UnsupportedOperationException("Not allowed to update training");
	}
}
