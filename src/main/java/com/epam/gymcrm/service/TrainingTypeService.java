package com.epam.gymcrm.service;

import com.epam.gymcrm.entity.TrainingType;
import com.epam.gymcrm.exception.UserNotFoundException;
import com.epam.gymcrm.repository.TrainingTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
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
			log.error("Error while retrieving training type: {}", e.getMessage());
		}
		return null;
	}

}
