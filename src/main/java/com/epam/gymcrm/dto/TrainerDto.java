package com.epam.gymcrm.dto;

import com.epam.gymcrm.entity.Trainee;
import com.epam.gymcrm.entity.Training;
import com.epam.gymcrm.entity.TrainingType;
import com.epam.gymcrm.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class TrainerDto {

	@NotNull
	private TrainingType trainingType2;

	@NotNull
	private User user1;

	@Nullable
	private Set<Trainee> traineeSet;

	@Nullable
	private Set<Training> trainingSet;

}
