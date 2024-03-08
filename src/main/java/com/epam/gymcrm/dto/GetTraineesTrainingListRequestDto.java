package com.epam.gymcrm.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@Builder
@AllArgsConstructor
public class GetTraineesTrainingListRequestDto {

	@NotNull
	private String traineeUsername;

	@Nullable
	private String periodFrom;

	@Nullable
	private String periodTo;

	@Nullable
	private String trainerUsername;

	@Nullable
	private String trainingTypeName;

}
