package com.epam.gymapplication.dtos;

import java.util.List;

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
public class TrainerProfileDTO {
//	First Name
//	• Last Name
//	• Specialization
//	• Is Active
//	• Trainees List
//	1. Trainee Username
//	2. Trainee First Name
//	3. Trainee Last Name
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@Pattern(regexp ="^(?)(fitness|yoga|Zumba|stretching|resistance)$",
			message="Specialization should be fitness or yoga or Zumba or stretching or resistance")
	private String specialization;
	private boolean isActive;
	private List<TraineeDTO> trainees;

}
