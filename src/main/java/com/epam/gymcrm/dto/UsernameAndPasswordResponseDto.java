package com.epam.gymcrm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@AllArgsConstructor
public class UsernameAndPasswordResponseDto {

	@NotNull
	private String username;

	@NotNull
	private String password;

}
