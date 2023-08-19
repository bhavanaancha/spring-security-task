package com.epam.dto;

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
public class ReportRequestDTO {

	private String userName;
	private String firstName;
	private String lastName;
	private boolean status;
	private LocalDate trainingDate;
	private int duration;
	
}
