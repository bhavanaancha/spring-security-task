package com.epam.gymapplication.dtos;

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
public class TrainerResponseDTO {
	@NotBlank
	private String username;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@Pattern(regexp ="^(?)(fitness|yoga|Zumba|stretching|resistance)$",
			message="Specialization should be fitness or yoga or Zumba or stretching or resistance")
	private String specialization;
	

}
