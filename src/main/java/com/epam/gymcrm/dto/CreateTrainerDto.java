package com.epam.gymcrm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@AllArgsConstructor
public class CreateTrainerDto {

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@NotNull
	private Long specialization;

}
