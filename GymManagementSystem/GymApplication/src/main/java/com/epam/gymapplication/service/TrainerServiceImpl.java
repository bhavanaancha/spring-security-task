package com.epam.gymapplication.service;

import com.epam.gymapplication.utility.CredentialGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.epam.dto.MailRequestDTO;
import com.epam.gymapplication.dtos.CredentialsDTO;
import com.epam.gymapplication.dtos.TrainerDTO;
import com.epam.gymapplication.dtos.TrainerProfileDTO;
import com.epam.gymapplication.dtos.TrainerUpdateRequestDTO;
import com.epam.gymapplication.entity.Trainer;
import com.epam.gymapplication.entity.TrainingType;
import com.epam.gymapplication.entity.User;
import com.epam.gymapplication.exceptions.TrainerException;
import com.epam.gymapplication.exceptions.UserException;
import com.epam.gymapplication.repository.TraineeRepo;
import com.epam.gymapplication.repository.TrainerRepo;
import com.epam.gymapplication.repository.TrainingRepo;
import com.epam.gymapplication.repository.TrainingTypeRepo;
import com.epam.gymapplication.repository.UserRepo;
import com.epam.gymapplication.service.interfaces.TrainerService;
import com.epam.gymapplication.utility.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrainerServiceImpl implements TrainerService {
	@Autowired
	UserRepo userRepo;

	@Autowired
	TraineeRepo traineeRepo;

	@Autowired
	TrainingTypeRepo trainingTypeRepo;

	@Autowired
	TrainerRepo trainerRepo;

	@Autowired
	TrainingRepo trainingRepo;

	@Autowired
	TraineeServiceImpl traineeService;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	CredentialGenerator credentialGenerator;
	@Autowired
	CommunicationService communicationService;

	@Override
	public CredentialsDTO registerTrainer(TrainerDTO userDto) {
		log.info("Registering trainer with email: {}", userDto.getEmail());
		String password=credentialGenerator.generateRandomPassword(6);
		User user = objectMapper.createTrainerUser(userDto);
		user.setPassword(encoder.encode(password));
		userRepo.save(user);
		log.info("trainer successfully got saved as user");
		TrainingType trainingType = findTrainingType(userDto.getSpecialization());
		Trainer trainer = Trainer.builder().specializationId(trainingType).user(user).build();
		trainerRepo.save(trainer);
		MailRequestDTO notification = objectMapper.mapToMailRequestDTO(user,password);
		communicationService.sendNotification(notification);
		log.info("Trainer registered successfully with email: {}", userDto.getEmail());
		return CredentialsDTO.builder().userName(userDto.getEmail()).password(user.getPassword()).build();
	}

	@Override
	public TrainerProfileDTO getTrainerProfile(String username) {
		log.info("Retrieving trainer profile for username: {}", username);
		Trainer trainer = findTrainer(username);
		log.info("Trainer profile retrieved successfully for username: {}", username);
		return objectMapper.getTraineeProfileDTO(trainer);
	}

	@Override
	@Transactional
	public TrainerProfileDTO updateTrainerProfile(TrainerUpdateRequestDTO trainerDto) {
		log.info("Updating trainer profile for username: {}", trainerDto.getUsername());
		User user = findUser(trainerDto.getUsername());
		user.setActive(trainerDto.isActive());
		user.setFirstName(trainerDto.getFirstName());
		user.setLastName(trainerDto.getLastName());
		user.setUserName(trainerDto.getUsername());
		Trainer trainer = trainerRepo.findByUserId(user.getId())
				.orElseThrow(() -> new TrainerException("trainer with this id can't be found"));
		trainer.setSpecializationId(trainer.getSpecializationId());
		MailRequestDTO notification = objectMapper.mapToTrainerUpdateMailRequestDTO(trainer);
		communicationService.sendNotification(notification);
		log.info("Trainer profile updated successfully for username: {}", trainerDto.getUsername());
		return objectMapper.getTraineeProfileDTO(trainer);
	}

	private Trainer findTrainer(String trainerName)  {
		return trainerRepo.findByUserUserName(trainerName)
				.orElseThrow(() -> new TrainerException("Trainer not found with name: " + trainerName));
	}

	private User findUser(String userName)  {
		return userRepo.findByUserName(userName)
				.orElseThrow(() -> new UserException("user not found with this username " + userName));
	}

	private TrainingType findTrainingType(String specialization)  {
		return trainingTypeRepo.findByTrainingTypeName(specialization)
				.orElseThrow(() -> new TrainerException("The specialization " + specialization + " doesn't exists"));
	}
}
