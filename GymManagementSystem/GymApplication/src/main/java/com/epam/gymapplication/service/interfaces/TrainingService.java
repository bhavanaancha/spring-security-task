package com.epam.gymapplication.service.interfaces;

import java.util.List;

import com.epam.gymapplication.dtos.TraineeTrainingRequest;
import com.epam.gymapplication.dtos.TraineeTrainingResponseDTO;
import com.epam.gymapplication.dtos.TrainerResponseTrainingDTO;
import com.epam.gymapplication.dtos.TrainerTrainingRequestDTO;
import com.epam.gymapplication.dtos.TrainingDTO;
import com.epam.gymapplication.exceptions.TraineeException;

public interface TrainingService {
	TrainingDTO addTraining(TrainingDTO trainingDto) throws TraineeException;
	List<TrainerResponseTrainingDTO> getTrainerTrainingDetails(TrainerTrainingRequestDTO training);
	List<TraineeTrainingResponseDTO> getTraineeTrainingDetails(TraineeTrainingRequest training) throws TraineeException;

}
