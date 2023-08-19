package com.epam.gymapplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.gymapplication.entity.Trainee;

public interface TraineeRepo extends JpaRepository<Trainee, Integer>{

	Optional<Trainee> findByUserId(int userId);

	Optional<Trainee> findByUserUserName(String traineeName);

}
