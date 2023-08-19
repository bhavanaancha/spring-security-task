package com.epam.model;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    @Column(nullable = false)
//    private String firstName;
//    @Column(nullable = false)
//    private String lastName;
//    @Column(unique = true, nullable = false)
//    private String username;
//    @Column(nullable = false)
//    private String password;
//    @Column(nullable = false)
//    private boolean isActive;
//    @Column(nullable = false)
//    String eMail;
//    @Column(nullable = false)
//    LocalDate createdDate = LocalDate.now();
	
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

}
