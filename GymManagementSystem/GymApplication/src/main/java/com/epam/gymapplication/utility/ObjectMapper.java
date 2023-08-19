package com.epam.gymapplication.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.MailRequestDTO;
import com.epam.dto.ReportRequestDTO;
import com.epam.gymapplication.dtos.TraineeDTO;
import com.epam.gymapplication.dtos.TraineeProfileDTO;
import com.epam.gymapplication.dtos.TraineeRequestDTO;
import com.epam.gymapplication.dtos.TraineeTrainingResponseDTO;
import com.epam.gymapplication.dtos.TrainerDTO;
import com.epam.gymapplication.dtos.TrainerProfileDTO;
import com.epam.gymapplication.dtos.TrainerResponseDTO;
import com.epam.gymapplication.dtos.TrainerResponseTrainingDTO;
import com.epam.gymapplication.dtos.TrainingDTO;
import com.epam.gymapplication.entity.Trainee;
import com.epam.gymapplication.entity.Trainer;
import com.epam.gymapplication.entity.Training;
import com.epam.gymapplication.entity.TrainingType;
import com.epam.gymapplication.entity.User;
import com.epam.gymapplication.exceptions.TrainerException;
import com.epam.gymapplication.repository.TrainerRepo;
import com.epam.gymapplication.repository.TrainingTypeRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ObjectMapper {
	@Autowired
	TrainerRepo trainerRepo;
	@Autowired
	TrainingTypeRepo trainingTypeRepo;
	@Autowired
	CredentialGenerator generator;
    
    public List<TrainerResponseDTO> getTrainersList(List<Trainer> trainers){
    	log.info("Mapping trainer details to TrainerResponseDTO");
		  return trainers.stream()
		            .map(trainer -> 
		                 TrainerResponseDTO.builder()
		                        .firstName(trainer.getUser().getFirstName())
		                        .lastName(trainer.getUser().getLastName())
		                        .username(trainer.getUser().getUserName())
		                        .specialization(trainer.getSpecializationId().getTrainingTypeName())
		                        .build()
		            ).toList();
    }
    
    public TraineeProfileDTO mapToTraineeProfileDTO(Trainee trainee) {
    	log.info("Mapping trainee details to TraineeProfileDTO");
    	return TraineeProfileDTO.builder()
	            .address(trainee.getAddress())
	            .dateOfBirth(trainee.getDateOfBirth())
	            .email(trainee.getUser().getEmail())
	            .firstName(trainee.getUser().getFirstName())
	            .lastName(trainee.getUser().getLastName())
	            .isActive(trainee.getUser().isActive())
	            .trainers(getTrainersList(trainee.getTrainers()))
	            .build();
    }
    
    public List<TraineeDTO> getTraineesList(List<Trainee> trainees){
    	log.info("getter trainees list");
    	return trainees.stream()
                .map(trainee -> 
                TraineeDTO.builder()
                       .firstName(trainee.getUser().getFirstName())
                       .lastName(trainee.getUser().getLastName())
                       .username(trainee.getUser().getUserName())
                       .build()
           ).toList();
    }
    public TrainerProfileDTO getTraineeProfileDTO(Trainer trainer) {
    	log.info("Mapping trainer details to TrainerProfileDTO");
    	return TrainerProfileDTO.builder().firstName(trainer.getUser().getFirstName())
		.lastName(trainer.getUser().getLastName())
		.isActive(trainer.getUser().isActive())
		.specialization(trainer.getSpecializationId().getTrainingTypeName())
		.trainees(getTraineesList(trainer.getTrainees()))
		.build();
    }
    
    public User createTraineeUser(TraineeRequestDTO trainee) {
    	log.info("creating trainee as a user");
//    	String password=encoder.encode(generator.generateRandomPassword(6));
    	return User.builder()
		.email(trainee.getEmail())
		.firstName(trainee.getFirstName())
		.lastName(trainee.getLastName())
		.userName(trainee.getEmail())
//		.password(generator.generateRandomPassword(6))
		.isActive(trainee.isActive())
		.build();
    }
    
    public User createTrainerUser(TrainerDTO trainer) {
    	log.info("creating trainer as a user");
//    	String password=encoder.encode(generator.generateRandomPassword(6));
    	
    	return User.builder()
	            .firstName(trainer.getFirstName())
	            .lastName(trainer.getLastName())
	            .email(trainer.getEmail())
	            .userName(trainer.getEmail())
//	            .password(generator.generateRandomPassword(6))
	            .isActive(true)
	            .build();
    }
    
    public List<TrainerResponseTrainingDTO> mapToTrainerResponseTrainingDTO(List<Training> trainings){
    	return trainings.stream()
        .map(trainingItem -> TrainerResponseTrainingDTO.builder()
                .trainingName(trainingItem.getTrainingName())
                .trainingDate(trainingItem.getTrainingDate())
                .trainingType(trainingItem.getTrainingTypeId().getTrainingTypeName())
                .duration(trainingItem.getDuration())
                .traineeName(trainingItem.getTrainee().getUser().getFirstName()+ " " + trainingItem.getTrainee().getUser().getLastName())
                .build()).toList();
    }
    
    public List<TraineeTrainingResponseDTO> mapToTraineeResponseTrainingDTO(List<Training> trainings){
    	
    	return trainings.stream()
        .map(trainingItem -> TraineeTrainingResponseDTO.builder()
            .trainingName(trainingItem.getTrainingName())
            .trainingDate(trainingItem.getTrainingDate())
            .trainingType(trainingItem.getTrainingTypeId().getTrainingTypeName())
            .duration(trainingItem.getDuration())
            .trainerName(trainingItem.getTrainer().getUser().getFirstName() + " " + trainingItem.getTrainer().getUser().getLastName())
            .build()).toList();
    }
    
    public Training createTrainingObject(TrainingDTO trainingDto, Trainee trainee, Trainer trainer) {
        TrainingType trainingType = trainingTypeRepo.findById(trainer.getSpecializationId().getId())
                                                    .orElseThrow(() -> new TrainerException("Training type not found"));

        return Training.builder()
                       .trainee(trainee)
                       .trainer(trainer)
                       .trainingDate(trainingDto.getTrainingDate())
                       .trainingName(trainingDto.getTrainingName())
                       .trainingTypeId(trainingType)
                       .duration(trainingDto.getDuration())
                       .build();
    }
    
   
    public ReportRequestDTO mapToReportRequestDTO(Training training) {
    	log.info(training.getTrainer().getUser().getUserName());
    	return ReportRequestDTO.builder().duration(training.getDuration())
    			.firstName(training.getTrainer().getUser().getFirstName())
    			.lastName(training.getTrainer().getUser().getLastName())
    			.userName(training.getTrainer().getUser().getUserName())
    			.status(training.getTrainer().getUser().isActive())
    			.trainingDate(training.getTrainingDate())
    			.build();
}
    public MailRequestDTO mapToMailRequestDTO(User user,String password) {
    	Map<String, String> details=new HashMap<>();
		details.put("subject", "registration confirmed");
		details.put("tagLine", "your registration is confirmed.");
		details.put("username", user.getUserName());
		details.put("password", password);
		return MailRequestDTO.builder().details(details)
				.ccMails(List.of(user.getEmail())).toMails(List.of(user.getEmail())).build();
    	
    }
    
    public MailRequestDTO mapToTraineeUpdateMailRequestDTO(Trainee trainee) {
    	 Map<String, String> details=new HashMap<>();
 		details.put("subject", "your gym profile updated");
 		details.put("tagLine", "your profile has updated. Here is your profile");
 		details.put("username",trainee.getUser().getUserName());
 		details.put("firstName", trainee.getUser().getFirstName());
 		details.put("activeStatus", trainee.getUser().isActive()?"active":"InActive");
 		return MailRequestDTO.builder().details(details)
		.ccMails(List.of(trainee.getUser().getEmail())).toMails(List.of(trainee.getUser().getEmail())).build();
    }
    
    public MailRequestDTO mapToTrainerUpdateMailRequestDTO(Trainer trainer) {
   	 Map<String, String> details=new HashMap<>();
		details.put("subject", "your gym profile updated");
		details.put("tagLine", "your profile has updated. Here is your profile");
		details.put("username",trainer.getUser().getUserName());
		details.put("firstName", trainer.getUser().getFirstName());
		details.put("activeStatus", trainer.getUser().isActive()?"active":"InActive");
		return MailRequestDTO.builder().details(details)
		.ccMails(List.of(trainer.getUser().getEmail())).toMails(List.of(trainer.getUser().getEmail())).build();
   }
    public MailRequestDTO mapToMailRequestDTO(Training training) {
    	Map<String, String> details=new HashMap<>();
		details.put("subject", "your training got added");
		details.put("trainer", training.getTrainer().getUser().getUserName());
		details.put("trainee", training.getTrainee().getUser().getUserName());
		details.put("training type", training.getTrainingTypeId().getTrainingTypeName());
	return MailRequestDTO.builder().details(details)
			.ccMails(List.of(training.getTrainer().getUser().getEmail())).toMails(List.of(training.getTrainer().getUser().getEmail())).build();
    }
    
}
