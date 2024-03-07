package com.epam.gymcrm.dto.request;

import com.epam.gymcrm.dto.response.TrainerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UpdateTraineesTrainerListDto {

	@NotNull
	private String traineeUsername;

	@Nullable
	List<UsernameDto> trainerList;

}
