package com.epam.gymapplication.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TrainerUpdateRequestDTO {
//	Username (required) 
//	• First Name (required) 
//	• Last Name (required)
//	• Specialization (read only)
//	• Is Active (required) 
	@NotBlank
	private String username;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@Pattern(regexp ="^(?)(fitness|yoga|Zumba|stretching|resistance)$",
			message="Specialization should be fitness or yoga or Zumba or stretching or resistance")
	@Schema(accessMode = AccessMode.READ_ONLY)
	private String specialization;
	private boolean isActive;	
}
