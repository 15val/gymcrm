package com.epam.gymcrm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UsernameAndIsActiveDto {

	@NotNull
	private String username;

	@NotNull
	private String isActive;

}
