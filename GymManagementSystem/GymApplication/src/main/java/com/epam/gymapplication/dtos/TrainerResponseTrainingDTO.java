package com.epam.gymapplication.dtos;

import java.time.LocalDate;

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
public class TrainerResponseTrainingDTO {
	
	private String trainingName;
	private LocalDate trainingDate;
	private String trainingType;
	private int duration;
	private String traineeName;

}
