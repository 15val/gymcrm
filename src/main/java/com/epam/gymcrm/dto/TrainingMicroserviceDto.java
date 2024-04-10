package com.epam.gymcrm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TrainingMicroserviceDto {

	@NotNull
	private String trainerUsername;

	@NotNull
	private String trainerFirstName;

	@NotNull
	private String trainerLastName;

	@NotNull
	@JsonProperty("isActive")
	private Boolean isActive;

	@NotNull
	private Date trainingDate;

	@NotNull
	private Integer trainingDuration;

	@NotNull
	private String actionType;

}
