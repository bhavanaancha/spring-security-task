package com.epam.gymapplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.gymapplication.entity.Trainee;
import com.epam.gymapplication.entity.Trainer;

public interface TrainerRepo extends JpaRepository<Trainer, Integer>{
	Optional<Trainer> findByUserId(int id);

	Optional<Trainer> findByUserUserName(String username);
	List<Trainer> findByTraineesNotContaining(Trainee trainee);
	
}
