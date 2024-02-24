package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.entity.TrainingType;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.repository.TrainingTypeRepository;
import com.epam.gymcrm.utils.HibernateUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingTypeService {
	private final TrainingTypeRepository trainingTypeRepository;



	public TrainingType getTrainingTypeById(Integer trainingTypeId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		TrainingType trainingType;
		try {
			trainingType = session.get(TrainingType.class, trainingTypeId);
			if(trainingType == null){
				throw new UserNotFoundException("Training type was not found");
			}
			else {
				return trainingType;
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
