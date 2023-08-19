package com.epam.gymapplication.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Training {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne()
	@JoinColumn(name="trainee_id", referencedColumnName="id")
	private Trainee trainee;
	@ManyToOne()
	@JoinColumn(name="trainer_id",referencedColumnName="id")
	private Trainer trainer;
	private String trainingName;
	@ManyToOne()
	@JoinColumn(name="training_type_id",referencedColumnName="id")
	private TrainingType trainingTypeId;
	private LocalDate trainingDate;
	private int duration;

}
