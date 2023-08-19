package com.epam.gymapplication.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingDTO {
	@NotBlank
	private String traineeName;
	@NotBlank
	private String trainerName;
	@NotBlank
	private String trainingName;
	@Pattern(regexp ="^(?)(fitness|yoga|Zumba|stretching|resistance)$",
			message="Specialization should be fitness or yoga or Zumba or stretching or resistance")
	private String trainingType;
	private LocalDate trainingDate;
	@NotNull
	private int duration;
}
