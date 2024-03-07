package com.epam.gymcrm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsernameDto {

	@Nullable
	private String username;

}
