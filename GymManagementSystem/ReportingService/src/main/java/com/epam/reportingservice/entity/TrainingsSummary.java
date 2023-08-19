package com.epam.reportingservice.entity;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document("Trainings")
public class TrainingsSummary {
	@Id
	private String username;
	private String firstName;
	private String lastName;
	private boolean status;
	private Map<Integer,Map<Integer,Map<Integer,Integer>>>summary;

}
