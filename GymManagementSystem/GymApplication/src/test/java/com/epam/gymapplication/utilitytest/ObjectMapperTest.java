package com.epam.gymapplication.utilitytest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.gymapplication.dtos.TraineeRequestDTO;
import com.epam.gymapplication.dtos.TrainerDTO;
import com.epam.gymapplication.dtos.TrainingDTO;
import com.epam.gymapplication.entity.Trainee;
import com.epam.gymapplication.entity.Trainer;
import com.epam.gymapplication.entity.Training;
import com.epam.gymapplication.entity.TrainingType;
import com.epam.gymapplication.entity.User;
import com.epam.gymapplication.exceptions.TrainerException;
import com.epam.gymapplication.repository.TraineeRepo;
import com.epam.gymapplication.repository.TrainerRepo;
import com.epam.gymapplication.repository.TrainingTypeRepo;
import com.epam.gymapplication.service.TraineeServiceImpl;
import com.epam.gymapplication.service.TrainerServiceImpl;
import com.epam.gymapplication.service.TrainingServiceImpl;
import com.epam.gymapplication.utility.CredentialGenerator;
import com.epam.gymapplication.utility.ObjectMapper;

@ExtendWith(MockitoExtension.class)
 class ObjectMapperTest {
	
	@InjectMocks
	private ObjectMapper objectMapper;
	@Mock
	TraineeServiceImpl traineeService;
	@Mock
	TrainerServiceImpl trainerService;
	@Mock
	TrainingServiceImpl trainingService;
	@Mock 
	TrainerRepo trainerRepo;
	@Mock 
	TraineeRepo traineeRepo;
	@Mock
	TrainingTypeRepo trainingTypeRepo;
	@Mock
	CredentialGenerator generator;
	User user;
	Trainer trainer;
	Trainee trainee;
	TrainingType type=new TrainingType();
	@BeforeEach
	void setup() {
		user=User.builder().firstName("first").lastName("last").email("a@gmail.com")
				.isActive(true).build();
		user.setRegistrationDate(java.time.LocalDate.parse("2001-07-31"));
		trainee=Trainee.builder().user(user).address("gnt").build();
		type.setTrainingTypeName("yoga");
		trainer=Trainer.builder().specializationId(type).trainees(List.of(trainee)).user(user).build();
		trainee.setTrainers(List.of(trainer));
	}
	
	 @Test
	     void testGetTrainersList() {
	        assertNotNull(objectMapper.getTrainersList(List.of(trainer)));
	    }
	 @Test
	 void mapToTraineeProfileDTO() {
		 assertNotNull(objectMapper.mapToTraineeProfileDTO(trainee));
	 }
	 @Test
	 void getTraineesList() {
		 assertNotNull(objectMapper.getTraineesList(List.of(trainee)));
	 }
	 @Test
	 void getTraineeProfileDto() {
		 assertNotNull(objectMapper.getTraineeProfileDTO(trainer));
	 }
	 @Test
	 void createTraineeUser() {
		 TraineeRequestDTO request=TraineeRequestDTO.builder().address("gnt").email("a@gmail.com").build();
		 assertNotNull(objectMapper.createTraineeUser(request));
	 }
	 @Test
	 void createTrainerUser() {
		 TrainerDTO request=new TrainerDTO();
		 assertNotNull(objectMapper.createTrainerUser(request));
	 }
	 @Test
	 void mapToTrainerResponseTrainingDTO() {
		 Training training=new Training();
		 training.setTrainee(trainee);
		 training.setTrainer(trainer);
		 type.setTrainingTypeName("yoga");
		 training.setTrainingTypeId(type);
		 assertNotNull(objectMapper.mapToTrainerResponseTrainingDTO(List.of(training)));
	 }
	 @Test
	 void mapToTraineeResponseTrainingDTO() {
		 Training training=new Training();
		 training.setTrainee(trainee);
		 training.setTrainer(trainer);
		 type.setTrainingTypeName("yoga");
		 training.setTrainingTypeId(type);
		 assertNotNull(objectMapper.mapToTraineeResponseTrainingDTO(List.of(training)));
	 }
	 @Test
	 void createTrainingObject() {
		 TrainingDTO trainingDto=new TrainingDTO();
		 trainingDto.setDuration(2);
		 trainingDto.setTrainingDate(LocalDate.now());
		 trainingDto.setTrainingName("hey");
		 when(trainingTypeRepo.findById(anyInt())).thenReturn(Optional.of(type));
		 assertNotNull(objectMapper.createTrainingObject(trainingDto, trainee, trainer));
	 }
	 @Test
	 void createTrainingObject_NotFound() {
		 TrainingDTO trainingDto=new TrainingDTO();
		 trainingDto.setDuration(2);
		 trainingDto.setTrainingDate(LocalDate.now());
		 trainingDto.setTrainingName("hey");
		 when(trainingTypeRepo.findById(anyInt())).thenReturn(Optional.empty());
		 assertThrows(TrainerException.class,()->objectMapper.createTrainingObject(trainingDto, trainee, trainer));
	 }
	 @Test
	 void mapToReportRequestDTO() {
		 Training training=new Training();
		 training.setTrainee(trainee);
		 training.setTrainer(trainer);
		 training.setTrainingDate(LocalDate.now());
		 type.setTrainingTypeName("yoga");
		 training.setTrainingTypeId(type);
		 assertNotNull(objectMapper.mapToReportRequestDTO(training));
	 }
	 @Test
	 void mapToMailRequestDTO() {
		 
		 assertNotNull(objectMapper.mapToMailRequestDTO(user,user.getPassword()));
	 }
	 @Test
	 void mapToTraineeMailRequestDTO() {
		 
		 assertNotNull(objectMapper.mapToTraineeUpdateMailRequestDTO(trainee));
	 }
	 @Test
	 void mapToInactiveTraineeMailRequestDTO() {
		 user.setActive(false);
		 assertNotNull(objectMapper.mapToTraineeUpdateMailRequestDTO(trainee));
	 }
	 @Test
	 void mapToTrainerMailRequestDTO() {
		 user.setActive(true);
		 assertNotNull(objectMapper.mapToTrainerUpdateMailRequestDTO(trainer));
	 }
	 @Test
	 void mapToTrainerInactiveMailRequestDTO() {
		 user.setActive(false);
		 assertNotNull(objectMapper.mapToTrainerUpdateMailRequestDTO(trainer));
	 }
	 @Test
	 void mapToTrainingMailRequestDTO() {
			 Training training=new Training();
			 training.setTrainee(trainee);
			 training.setTrainer(trainer);
			 training.setTrainingDate(LocalDate.now());
			 type.setTrainingTypeName("yoga");
			 training.setTrainingTypeId(type);
		 assertNotNull(objectMapper.mapToMailRequestDTO(training));
	 }
}
