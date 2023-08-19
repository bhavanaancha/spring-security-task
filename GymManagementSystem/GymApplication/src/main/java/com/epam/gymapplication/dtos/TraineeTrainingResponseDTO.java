package com.epam.gymapplication.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class TraineeTrainingResponseDTO {
	@NotBlank
	private String trainingName;
	private LocalDate trainingDate;
	@NotBlank
	private String trainingType;
	@NotNull
    private int duration;
    @NotBlank
    private String trainerName;
	

}
