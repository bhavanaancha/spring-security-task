package com.epam.gymapplication.entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;
	@Column(unique = true)
	private String email;
	@Column(unique=true)
	private String userName;
	private String password;
	private boolean isActive;
	@Builder.Default
	private LocalDate registrationDate=LocalDate.now();
	@OneToOne(mappedBy="user",cascade = CascadeType.ALL)
	private Trainer trainer;
	@OneToOne(mappedBy="user",cascade = CascadeType.ALL)
	private Trainee trainee;
	
	
	
}
