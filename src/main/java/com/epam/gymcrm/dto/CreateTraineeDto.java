package com.epam.gymcrm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@Builder
@AllArgsConstructor
public class CreateTraineeDto {

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@Nullable
	private String dateOfBirth;

	@Nullable
	private String address;

}
