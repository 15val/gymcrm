package com.epam.gymcrm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TrainingDto {

	@Nullable
	private String trainingName;

	@Nullable
	private String trainingDate;

	@Nullable
	private String trainingType;

	@Nullable
	private String trainingDuration;

	@Nullable
	private String username;

}
