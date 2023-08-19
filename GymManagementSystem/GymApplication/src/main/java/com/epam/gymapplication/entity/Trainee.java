package com.epam.gymapplication.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
public class Trainee {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	@JoinColumn(name="user_id",referencedColumnName = "id")
	private User user;
	
	@Temporal(TemporalType.DATE)
	private LocalDate dateOfBirth;
	private String address;
	
	@ManyToMany()
	@JoinTable(name="Trainee2Trainer", joinColumns=@JoinColumn(name="trainee_id")
	,inverseJoinColumns=@JoinColumn(name="trainer_id"))
	private List<Trainer> trainers;
	
	@OneToMany(mappedBy="trainee",cascade = CascadeType.ALL)
	private List<Training> training;
	
}
