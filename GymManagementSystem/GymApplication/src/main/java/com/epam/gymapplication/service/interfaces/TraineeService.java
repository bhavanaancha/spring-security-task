package com.epam.gymapplication.service.interfaces;

import java.util.List;

import com.epam.gymapplication.dtos.CredentialsDTO;
import com.epam.gymapplication.dtos.TraineeProfileDTO;
import com.epam.gymapplication.dtos.TraineeRequestDTO;
import com.epam.gymapplication.dtos.TraineeUpdateRequestDTO;
import com.epam.gymapplication.dtos.TrainerResponseDTO;
import com.epam.gymapplication.exceptions.TraineeException;

public interface TraineeService {
	CredentialsDTO registerTrainee(TraineeRequestDTO traineeDto);
	TraineeProfileDTO getTrainee(String username) throws TraineeException;
	void deleteTraineeProfile(String username) throws TraineeException ;
	List<TrainerResponseDTO> updateTraineeTrainers(String traineeName, List<String>trainers) throws TraineeException;
	TraineeProfileDTO updateTraineeProfile(TraineeUpdateRequestDTO traineeDto) throws TraineeException;
	List<TrainerResponseDTO> unassignedTrainers(String username) throws TraineeException;
	
}
