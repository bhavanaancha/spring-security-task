package com.epam.gymapplication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.MailRequestDTO;
import com.epam.dto.ReportRequestDTO;
import com.epam.gymapplication.dtos.TraineeTrainingRequest;
import com.epam.gymapplication.dtos.TraineeTrainingResponseDTO;
import com.epam.gymapplication.dtos.TrainerResponseTrainingDTO;
import com.epam.gymapplication.dtos.TrainerTrainingRequestDTO;
import com.epam.gymapplication.dtos.TrainingDTO;
import com.epam.gymapplication.entity.Trainee;
import com.epam.gymapplication.entity.Trainer;
import com.epam.gymapplication.entity.Training;
import com.epam.gymapplication.exceptions.TraineeException;
import com.epam.gymapplication.exceptions.TrainerException;
import com.epam.gymapplication.repository.TraineeRepo;
import com.epam.gymapplication.repository.TrainerRepo;
import com.epam.gymapplication.repository.TrainingRepo;
import com.epam.gymapplication.repository.TrainingTypeRepo;
import com.epam.gymapplication.repository.UserRepo;
import com.epam.gymapplication.service.interfaces.TrainingService;
import com.epam.gymapplication.utility.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrainingServiceImpl implements TrainingService {
	@Autowired
	UserRepo userRepo;
	@Autowired
	TrainerRepo trainerRepo;
	@Autowired
	TraineeRepo traineeRepo;
	@Autowired
	TrainingTypeRepo trainingTypeRepo;
	@Autowired
	TrainingRepo trainingRepo;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	CommunicationService communicationService;

	@Override
	public TrainingDTO addTraining(TrainingDTO trainingDto) throws TraineeException {
		log.info("Adding training for trainee: {} and trainer: {}", trainingDto.getTraineeName(),
				trainingDto.getTrainerName());
		Trainee trainee = findTrainee(trainingDto.getTraineeName());
		Trainer trainer = findTrainerAndAssociation(trainingDto.getTrainerName(), trainee);
		Training training = objectMapper.createTrainingObject(trainingDto, trainee, trainer);
		trainingRepo.save(training);
		MailRequestDTO notification = objectMapper.mapToMailRequestDTO(training);
		communicationService.sendNotification(notification);
		ReportRequestDTO trainingSummary = objectMapper.mapToReportRequestDTO(training);
		communicationService.sendTrainingReport(trainingSummary);
		log.info("Training added successfully for trainee: {} and trainer: {}", trainingDto.getTraineeName(),
				trainingDto.getTrainerName());
		return trainingDto;
	}

	@Override
	public List<TrainerResponseTrainingDTO> getTrainerTrainingDetails(TrainerTrainingRequestDTO training) {
		log.info("Retrieving trainer training details for username: {}", training.getUsername());
		Trainer trainer = findTrainer(training.getUsername());
		List<Training> trainings = trainingRepo.findTrainingsForTrainer(trainer.getUser().getUserName(),
				training.getPeriodFrom(), training.getPeriodTo(), training.getTraineeName());
		log.info("Trainer training details retrieved successfully");
		return objectMapper.mapToTrainerResponseTrainingDTO(trainings);
	}

	@Override
	public List<TraineeTrainingResponseDTO> getTraineeTrainingDetails(TraineeTrainingRequest training)
			throws TraineeException {
		log.info("Retrieving trainee training details for username: {}", training.getUsername());
		Trainee trainee = findTrainee(training.getUsername());
		List<Training> trainings = trainingRepo.findTrainingsForTrainee(trainee.getUser().getUserName(),
				training.getPeriodFrom(), training.getPeriodTo(), training.getTrainerName(),
				training.getTrainingType());
		log.info("Trainee training details retrieved successfully for username: {}", training.getUsername());
		return objectMapper.mapToTraineeResponseTrainingDTO(trainings);
	}

	private Trainee findTrainee(String traineeName) throws TraineeException {
		return traineeRepo.findByUserUserName(traineeName)
				.orElseThrow(() -> new TraineeException("Trainee not found with name: " + traineeName));
	}

	private Trainer findTrainer(String trainerName) throws TrainerException {
		return trainerRepo.findByUserUserName(trainerName)
				.orElseThrow(() -> new TrainerException("Trainer not found with name: " + trainerName));
	}

	private Trainer findTrainerAndAssociation(String trainerName, Trainee trainee) throws TrainerException {
		Trainer trainer = trainerRepo.findByUserUserName(trainerName)
				.orElseThrow(() -> new TrainerException("Trainer not found with name: " + trainerName));

		if (!trainee.getTrainers().contains(trainer)) {
			throw new TrainerException("No association with the trainee and the trainer");
		}
		return trainer;
	}

}
