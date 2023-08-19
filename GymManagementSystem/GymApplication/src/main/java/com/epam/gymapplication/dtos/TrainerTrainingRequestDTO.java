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
public class TrainerTrainingRequestDTO {
	private String username;
	private LocalDate periodFrom;
	private LocalDate periodTo;
	private String traineeName;

}
