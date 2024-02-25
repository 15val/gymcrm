package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.entity.TrainingType;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.repository.TrainingTypeRepository;
import com.epam.gymcrm.utils.HibernateUtil;
import jakarta.transaction.Transactional;
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



	@Transactional
	public TrainingType getTrainingTypeById(Long trainingTypeId) {
		try {
			TrainingType trainingType = trainingTypeRepository.findById(trainingTypeId).orElse(null);
			if(trainingType == null){
				throw new UserNotFoundException("Training type was not found");
			}
			else {
				return trainingType;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
