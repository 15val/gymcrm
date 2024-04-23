package com.epam.gymcrm.facade;

import com.epam.gymcrm.dto.GetTrainingTypeListDto;
import com.epam.gymcrm.dto.TrainingTypeDto;
import com.epam.gymcrm.entity.TrainingType;
import com.epam.gymcrm.repository.TrainingTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingTypeFacade {

	private final TrainingTypeRepository trainingTypeRepository;

	public GetTrainingTypeListDto getTrainingTypeList(){
		try {
			List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
			List<TrainingTypeDto> trainingTypeDtoList = new ArrayList<>();
			for (TrainingType trainingType : trainingTypeList) {
				TrainingTypeDto trainingTypeDto = TrainingTypeDto.builder()
						.trainingTypeId(String.valueOf(trainingType.getId()))
						.trainingTypeName(trainingType.getTrainingTypeName())
						.build();
				trainingTypeDtoList.add(trainingTypeDto);
			}
			return new GetTrainingTypeListDto(trainingTypeDtoList);
		}
		catch (Exception e) {
			log.error("Facade: Error while retrieving training types : {}", e.getMessage());
			throw e;
		}
	}

}
