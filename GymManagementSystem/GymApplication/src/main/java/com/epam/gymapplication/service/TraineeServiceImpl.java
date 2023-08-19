package com.epam.gymapplication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epam.gymapplication.utility.CredentialGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.epam.dto.MailRequestDTO;
import com.epam.gymapplication.dtos.CredentialsDTO;
import com.epam.gymapplication.dtos.TraineeProfileDTO;
import com.epam.gymapplication.dtos.TraineeRequestDTO;
import com.epam.gymapplication.dtos.TraineeUpdateRequestDTO;
import com.epam.gymapplication.dtos.TrainerResponseDTO;
import com.epam.gymapplication.entity.Trainee;
import com.epam.gymapplication.entity.Trainer;
import com.epam.gymapplication.entity.User;
import com.epam.gymapplication.exceptions.TraineeException;
import com.epam.gymapplication.exceptions.UserException;
import com.epam.gymapplication.repository.TraineeRepo;
import com.epam.gymapplication.repository.TrainerRepo;
import com.epam.gymapplication.repository.TrainingRepo;
import com.epam.gymapplication.repository.TrainingTypeRepo;
import com.epam.gymapplication.repository.UserRepo;
import com.epam.gymapplication.service.interfaces.TraineeService;
import com.epam.gymapplication.utility.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TraineeServiceImpl implements TraineeService {

	@Autowired
	TraineeRepo traineeRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	TrainerRepo trainerRepo;
	@Autowired
	TrainingRepo trainingRepo;
	@Autowired
	TrainingTypeRepo trainingTypeRepo;
	@Autowired
	TrainerServiceImpl trainerService;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	CredentialGenerator credentialGenerator;
	@Autowired
	CommunicationService communicationService;

	@Override
	public CredentialsDTO registerTrainee(TraineeRequestDTO traineeDto) {
		log.info("Registering trainee: {}", traineeDto.getEmail());
		String password=credentialGenerator.generateRandomPassword(6);
		CredentialsDTO credentials = new CredentialsDTO();
		User user = objectMapper.createTraineeUser(traineeDto);
		user.setPassword(encoder.encode(password));
		userRepo.save(user);
		log.info("User saved: {}", user.getUserName());
		Trainee trainee = Trainee.builder().user(user).address(traineeDto.getAddress())
				.dateOfBirth(traineeDto.getDateOfBirth()).build();
		traineeRepo.save(trainee);
		MailRequestDTO notification = objectMapper.mapToMailRequestDTO(user,password);
		communicationService.sendNotification(notification);
		log.info("Trainee saved with ID: {}", trainee.getId());
		credentials.setUserName(traineeDto.getEmail());
		credentials.setPassword(user.getPassword());
		log.info("Credentials generated and returned for trainee: {}", traineeDto.getEmail());
		return credentials;
	}

	@Override
	public void deleteTraineeProfile(String username) throws TraineeException  {
		log.info("Deleting trainee profile for username: {}", username);
		Trainee trainee = traineeRepo.findByUserUserName(username)
				.orElseThrow(() -> new TraineeException("trainee can't be found with this username"));
//		trainee.getTrainers().forEach(trainer -> trainer.getTrainees().remove(trainee));
		userRepo.deleteById(trainee.getUser().getId());
		log.info("Trainee profile deleted successfully for username: {}", username);
	}

	@Override
	public TraineeProfileDTO getTrainee(String username) throws TraineeException{
		log.info("Retrieving trainee profile for username: {}", username);
		Trainee trainee = findTrainee(username);
		log.info("Trainee profile retrieved successfully for username: {}", username);
		return objectMapper.mapToTraineeProfileDTO(trainee);
	}

	@Override
	@Transactional
	public TraineeProfileDTO updateTraineeProfile(TraineeUpdateRequestDTO traineeDto) throws TraineeException {
		log.info("Updating trainee profile for username: {}", traineeDto.getUsername());
		Trainee trainee = findTrainee(traineeDto.getUsername());
		User user = trainee.getUser();
		user.setFirstName(traineeDto.getFirstName());
		user.setLastName(traineeDto.getLastName());
		user.setActive(traineeDto.isActive());
		trainee.setAddress(traineeDto.getAddress());
		trainee.setDateOfBirth(traineeDto.getDateOfBirth());

		MailRequestDTO notification = objectMapper.mapToTraineeUpdateMailRequestDTO(trainee);
		communicationService.sendNotification(notification);
		log.info("Trainee profile updated successfully for username: {}", traineeDto.getUsername());
		return objectMapper.mapToTraineeProfileDTO(trainee);
	}

	@Override
	public List<TrainerResponseDTO> unassignedTrainers(String username) throws TraineeException {
		Trainee trainee = findTrainee(username);
		List<Trainer> trainers = trainerRepo.findByTraineesNotContaining(trainee);
		return objectMapper.getTrainersList(trainers);
	}

	@Override
	@Transactional
	public List<TrainerResponseDTO> updateTraineeTrainers(String traineeName, List<String> trainerUsernames) throws TraineeException
			{
		log.info("Updating trainers for trainee with username: {}", traineeName);
		Trainee trainee = findTrainee(traineeName);
		List<User> users = userRepo.findAllByUserNameIn(trainerUsernames);
		List<Trainer> trainers = new ArrayList<>();
		List<TrainerResponseDTO> trainersDetails = users.stream().map(user -> {
			trainers.add(user.getTrainer());
			return TrainerResponseDTO.builder().firstName(user.getFirstName()).lastName(user.getLastName())
					.username(user.getUserName())
					.specialization(user.getTrainer().getSpecializationId().getTrainingTypeName()).build();
		}).toList();
		trainee.setTrainers(trainers);
		traineeRepo.save(trainee);
		log.info("Trainers updated successfully for trainee with username: {}", traineeName);
		return trainersDetails;
	}

	private Trainee findTrainee(String traineeName) throws TraineeException {
		return traineeRepo.findByUserUserName(traineeName)
				.orElseThrow(() -> new TraineeException("Trainee not found with name: " + traineeName));
	}
	
	

}
