package com.epam.gymcrm.dto.response;

import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.entity.TrainingType;
import com.epam.gymcrm.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerDto {

	@Nullable
	private String trainerUsername;

	@Nullable
	private String trainerFirstName;

	@Nullable
	private String trainerLastName;

	@Nullable
	private String specialization;

}
