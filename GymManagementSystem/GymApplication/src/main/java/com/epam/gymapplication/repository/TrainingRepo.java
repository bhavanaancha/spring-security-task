package com.epam.gymapplication.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epam.gymapplication.entity.Training;

public interface TrainingRepo extends JpaRepository<Training, Integer>{

	Optional<Training> findByTraineeId(int id);
	@Query("SELECT t FROM Training t " +
		       "JOIN t.trainee tr " +
		       "JOIN t.trainer tnr " +
		       "JOIN t.trainingTypeId tt " +
		       "WHERE tr.user.userName = :username " +
		       "AND (:periodFrom IS NULL OR t.trainingDate >= :periodFrom) " +
		       "AND (:periodTo IS NULL OR t.trainingDate <= :periodTo) " +
		       "AND (:trainerName IS NULL OR tnr.user.firstName LIKE %:trainerName% OR tnr.user.lastName LIKE %:trainerName%) " +
		       "AND (:trainingType IS NULL OR tt.trainingTypeName LIKE %:trainingType%)")
		List<Training> findTrainingsForTrainee(String username, LocalDate periodFrom, LocalDate periodTo, String trainerName, String trainingType);
	 
	 @Query("SELECT t FROM Training t "+
			    "JOIN t.trainee tr "+
			 	"JOIN t.trainer tnr "+
			 	"JOIN t.trainingTypeId tt "
	            + "WHERE tnr.user.userName = :username "
	            + "AND (:periodFrom IS NULL OR t.trainingDate >= :periodFrom) "
	            + "AND (:periodTo IS NULL OR t.trainingDate <= :periodTo) "
	            + "AND (:traineeName IS NULL OR tr.user.firstName LIKE %:traineeName% OR tr.user.lastName LIKE %:traineeName%) ")
	           
	List<Training> findTrainingsForTrainer(String username,LocalDate periodFrom,LocalDate periodTo,
            String traineeName);
	 
//	 @Query("SELECT t FROM Training t " +
//		       "JOIN t.trainer tr " +
//		       "WHERE tr.username = :trainerUsername " +
//		       "AND (t.trainingDate < :givenDate OR FUNCTION('DATE', t.trainingDate) = FUNCTION('DATE', :givenDate)) " +
//		       "AND FUNCTION('MONTH', t.trainingDate) = FUNCTION('MONTH', :givenDate) " +
//		       "AND FUNCTION('YEAR', t.trainingDate) = FUNCTION('YEAR', :givenDate)")
//	     List<Training> findActiveTrainingByTrainerAndDate(String trainerUsername, LocalDate givenDate);
	
}

