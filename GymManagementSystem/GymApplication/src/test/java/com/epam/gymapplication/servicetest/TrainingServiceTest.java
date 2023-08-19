package com.epam.gymapplication.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.gymapplication.dtos.TraineeTrainingRequest;
import com.epam.gymapplication.dtos.TraineeTrainingResponseDTO;
import com.epam.gymapplication.dtos.TrainerResponseTrainingDTO;
import com.epam.gymapplication.dtos.TrainerTrainingRequestDTO;
import com.epam.gymapplication.dtos.TrainingDTO;
import com.epam.gymapplication.entity.Trainee;
import com.epam.gymapplication.entity.Trainer;
import com.epam.gymapplication.entity.Training;
import com.epam.gymapplication.entity.TrainingType;
import com.epam.gymapplication.entity.User;
import com.epam.gymapplication.exceptions.TraineeException;
import com.epam.gymapplication.exceptions.TrainerException;
import com.epam.gymapplication.repository.TraineeRepo;
import com.epam.gymapplication.repository.TrainerRepo;
import com.epam.gymapplication.repository.TrainingRepo;
import com.epam.gymapplication.repository.TrainingTypeRepo;
import com.epam.gymapplication.repository.UserRepo;
import com.epam.gymapplication.service.CommunicationService;
import com.epam.gymapplication.service.TrainingServiceImpl;
import com.epam.gymapplication.utility.ObjectMapper;

@ExtendWith(MockitoExtension.class)
 class TrainingServiceTest {
	@Mock
	UserRepo userRepo;
	@Mock
	TrainerRepo trainerRepo;
	@Mock
	TraineeRepo traineeRepo;
	@Mock
	TrainingTypeRepo trainingTypeRepo;
	@Mock
	TrainingRepo trainingRepo;
	@Mock
	ObjectMapper objectMapper;
	@Mock
	CommunicationService communicationService;
	@InjectMocks
	TrainingServiceImpl trainingService;
	
	User user;
	Trainer trainer;
	Trainee trainee;
	TrainingType type = new TrainingType();
	
	@BeforeEach
	void setUser() {
		 user=User.builder().email("Sample@gmail.com").firstName("bhavna").lastName("ancha").isActive(true)
				.userName("Sample@gmail.com").password("something").registrationDate(LocalDate.now()).build();
		 user.setId(1);
		user.setRegistrationDate(java.time.LocalDate.parse("2001-07-31"));
		  trainer=Trainer.builder().user(user).specializationId(type).build();
		  trainer.setUser(user);
		  trainee = Trainee.builder().user(user).build();
		  user.setTrainee(trainee);
		  user.setTrainer(trainer);
		  trainee.setTrainers(List.of(trainer));
	}
	
	@Test
     void testAddTraining() throws TraineeException {
        TrainingDTO trainingDto = new TrainingDTO();
        Training training = new Training();
        when(traineeRepo.findByUserUserName(trainingDto.getTraineeName())).thenReturn(Optional.of(trainee));
        when(trainerRepo.findByUserUserName(trainingDto.getTrainerName())).thenReturn(Optional.of(trainer));
        when(objectMapper.createTrainingObject(trainingDto, trainee, trainer)).thenReturn(training);
        TrainingDTO result = trainingService.addTraining(trainingDto);
        assertEquals(trainingDto, result);
    }
	@Test
    void testAddTraining_TraineeNotFound() throws TraineeException {
       TrainingDTO trainingDto = new TrainingDTO();
       when(traineeRepo.findByUserUserName(trainingDto.getTraineeName())).thenReturn(Optional.empty());
       assertThrows(TraineeException.class,()->trainingService.addTraining(trainingDto));
   }
	@Test
    void testAddTraining_TrainerNotFound() {
       TrainingDTO trainingDto = new TrainingDTO();
       when(traineeRepo.findByUserUserName(trainingDto.getTraineeName())).thenReturn(Optional.of(trainee));
       when(trainerRepo.findByUserUserName(trainingDto.getTrainerName())).thenReturn(Optional.empty());
       assertThrows(TrainerException.class,()->trainingService.addTraining(trainingDto));
   }
//	@Test
//    void testAddTraining_TrainerTraineeAssNotFound() {
//       TrainingDTO trainingDto = new TrainingDTO();
//       when(traineeRepo.findByUserUserName(trainingDto.getTraineeName())).thenReturn(Optional.of(trainee));
//       when(trainerRepo.findByUserUserName(trainingDto.getTrainerName())).thenReturn(Optional.of(trainer));
//       assertThrows(TrainerException.class,()->trainingService.addTraining(trainingDto));
//   }


	@Test
     void testGetTrainerTrainingDetails() {
        TrainerTrainingRequestDTO trainingRequestDTO = new TrainerTrainingRequestDTO();
        List<Training> trainings = new ArrayList<>();
        when(trainerRepo.findByUserUserName(trainingRequestDTO.getUsername())).thenReturn(Optional.of(trainer));
        when(trainingRepo.findTrainingsForTrainer(anyString(), any(), any(), any())).thenReturn(trainings);
        when(objectMapper.mapToTrainerResponseTrainingDTO(trainings)).thenReturn(new ArrayList<>());
        List<TrainerResponseTrainingDTO> result = trainingService.getTrainerTrainingDetails(trainingRequestDTO);
        verify(trainerRepo).findByUserUserName(trainingRequestDTO.getUsername());
        verify(trainingRepo).findTrainingsForTrainer(anyString(), any(), any(), any());
        verify(objectMapper).mapToTrainerResponseTrainingDTO(trainings);
        assertNotNull(result);
    }
	@Test
    void testGetTrainerTrainingDetails_TrainerNotFound() throws TrainerException{
       TrainerTrainingRequestDTO trainingRequestDTO = new TrainerTrainingRequestDTO();
       when(trainerRepo.findByUserUserName(trainingRequestDTO.getUsername())).thenReturn(Optional.empty());
       assertThrows(TrainerException.class,()->trainingService.getTrainerTrainingDetails(trainingRequestDTO));
   }

    @Test
     void testGetTraineeTrainingDetails() throws TraineeException {
        TraineeTrainingRequest trainingRequest = new TraineeTrainingRequest();
        List<Training> trainings = new ArrayList<>();
        when(traineeRepo.findByUserUserName(trainingRequest.getUsername())).thenReturn(Optional.of(trainee));
        when(trainingRepo.findTrainingsForTrainee(anyString(), any(), any(), any(), any())).thenReturn(trainings);
        when(objectMapper.mapToTraineeResponseTrainingDTO(trainings)).thenReturn(new ArrayList<>());
        List<TraineeTrainingResponseDTO> result = trainingService.getTraineeTrainingDetails(trainingRequest);
        assertNotNull(result);
    }
    @Test
    void testGetTraineeTrainingDetails_TraineeNotFound() throws TraineeException {
       TraineeTrainingRequest trainingRequest = new TraineeTrainingRequest();
       when(traineeRepo.findByUserUserName(trainingRequest.getUsername())).thenReturn(Optional.empty());
       assertThrows(TraineeException.class,()->trainingService.getTraineeTrainingDetails(trainingRequest));
   }
   
	
	

}
