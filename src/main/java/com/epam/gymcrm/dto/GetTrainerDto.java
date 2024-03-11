package com.epam.gymcrm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class GetTrainerDto {

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@NotNull
	private String specialization;

	@NotNull
	private String isActive;

	@Nullable
	private List<TraineeDto> traineeList;

}
