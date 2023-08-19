package com.epam.gymapplication.dtos;

import java.time.LocalDate;

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
public class TraineeTrainingRequest {
	@NotBlank
	private String username;
	private LocalDate periodFrom;
	private LocalDate periodTo;
	@NotBlank
	private String trainerName;
	@Pattern(regexp ="^(?)(fitness|yoga|Zumba|stretching|resistance)$",
			message="Specialization should be fitness or yoga or Zumba or stretching or resistance")
	private String trainingType;

}
