package com.epam.gymcrm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@AllArgsConstructor
public class AddTrainingDto {

	@NotNull
	private String traineeUsername;

	@NotNull
	private String trainerUsername;

	@NotNull
	private String trainingName;

	@NotNull
	private String trainingDate;

	@NotNull
	private String trainingDuration;

}
