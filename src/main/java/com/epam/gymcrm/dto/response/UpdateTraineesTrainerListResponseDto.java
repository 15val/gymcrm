package com.epam.gymcrm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UpdateTraineesTrainerListResponseDto {

	@Nullable
	List<TrainerDto> trainerList;

}
