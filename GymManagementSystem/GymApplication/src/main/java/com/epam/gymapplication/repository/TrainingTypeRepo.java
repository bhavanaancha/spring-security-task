package com.epam.gymapplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.gymapplication.entity.TrainingType;

public interface TrainingTypeRepo extends JpaRepository<TrainingType, Integer>{
	
	 Optional<TrainingType> findByTrainingTypeName(String trainingTypeName);

}
