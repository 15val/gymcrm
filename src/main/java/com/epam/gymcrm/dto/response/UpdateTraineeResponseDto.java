package com.epam.gymcrm.dto.response;

import com.epam.gymcrm.entity.Trainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
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
