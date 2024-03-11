package com.epam.gymcrm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
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
