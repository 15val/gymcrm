package com.epam.gymcrm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UpdatePasswordDto {

	@NotNull
	private String username;

	@NotNull
	private String password;

	@NotNull
	private String newPassword;

}
