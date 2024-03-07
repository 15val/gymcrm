package com.epam.gymcrm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@Builder
@AllArgsConstructor
public class UpdateTraineeDto {

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

}