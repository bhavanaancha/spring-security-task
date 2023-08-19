package com.epam.gymapplication.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Trainer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	@JoinColumn(name="user_id",referencedColumnName = "id")
	 User user;
	
	@ManyToOne
	@JoinColumn(name="training_type_Id",referencedColumnName = "id")
	 TrainingType specializationId;
	
	@ManyToMany(mappedBy="trainers")
	 List<Trainee> trainees;
	
	@OneToMany(mappedBy="trainer",cascade = CascadeType.ALL)
	private List<Training> training;
}
