package com.epam.gymcrm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TraineeDto {

	@Nullable
	private String traineeUsername;

	@Nullable
	private String traineeFirstName;

	@Nullable
	private String traineeLastName;

}
