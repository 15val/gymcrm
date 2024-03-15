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
public class UpdateTraineeResponseDto {

	@NotNull
	private String username;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@Nullable
	private String dateOfBirth;

	@Nullable
	private String address;

	@NotNull
	private String isActive;

	@Nullable
	private List<TrainerDto> trainerList;

}
