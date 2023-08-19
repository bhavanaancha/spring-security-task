package com.epam.gymapplication.servicetest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.epam.gymapplication.dtos.TrainerDTO;
import com.epam.gymapplication.dtos.TrainerProfileDTO;
import com.epam.gymapplication.dtos.TrainerUpdateRequestDTO;
import com.epam.gymapplication.entity.Trainee;
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
import com.epam.gymapplication.service.CommunicationService;
import com.epam.gymapplication.service.TrainerServiceImpl;
import com.epam.gymapplication.utility.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest {
	@Mock
	UserRepo userRepo;

	@Mock
	TraineeRepo traineeRepo;

	@Mock
	TrainingTypeRepo trainingTypeRepo;

	@Mock
	TrainerRepo trainerRepo;

	@Mock
	TrainingRepo trainingRepo;

	@InjectMocks
	TrainerServiceImpl trainerService;

	@Mock
	ModelMapper mapper;
	@Mock
	ObjectMapper objectMapper;
	@Mock
	CommunicationService communicationService;
	
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
//		  user.setTrainee(trainee);
		  user.setTrainer(trainer);
//		  trainee.setTrainers(List.of(trainer));
	}
	
	@Test
     void testRegisterTrainer_Success() {
        TrainerDTO trainerDto = new TrainerDTO();
        trainerDto.setEmail("test@example.com");
        trainerDto.setSpecialization("SpecializationType");

        User user = new User();
        user.setUserName(trainerDto.getEmail());
        user.setPassword("testPassword");

        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName(trainerDto.getSpecialization());

        when(objectMapper.createTrainerUser(trainerDto)).thenReturn(user);
        when(trainingTypeRepo.findByTrainingTypeName(trainerDto.getSpecialization())).thenReturn(Optional.of(trainingType));
        assertNotNull(trainerService.registerTrainer(trainerDto));
       
    }

	@Test
     void testGetTrainerProfile() throws TrainerException {
		String username = "testUsername";

        Mockito.when(trainerRepo.findByUserUserName(username)).thenReturn(Optional.of(trainer));

        Mockito.when(objectMapper.getTraineeProfileDTO(trainer)).thenReturn(new TrainerProfileDTO());

        TrainerProfileDTO profileDto = trainerService.getTrainerProfile(username);

        assertNotNull(profileDto);

        Mockito.verify(trainerRepo).findByUserUserName(username);
	
	}
	
	@Test
     void testUpdateTrainerProfile() throws TrainerException {
        TrainerUpdateRequestDTO trainerDto = new TrainerUpdateRequestDTO();
        trainerDto.setSpecialization("yoga");
        trainerDto.setUsername("test_username");
        trainerDto.setFirstName("John");
        trainerDto.setLastName("Doe");
        trainerDto.setActive(true);
        when(userRepo.findByUserName(trainerDto.getUsername())).thenReturn(Optional.of(user));
        when(trainerRepo.findByUserId(user.getId())).thenReturn(Optional.of(trainer));
        when(objectMapper.getTraineeProfileDTO(trainer)).thenReturn(new TrainerProfileDTO());
        TrainerProfileDTO result = trainerService.updateTrainerProfile(trainerDto);
        assertNotNull(result);
    }
	@Test
    void testUpdateTrainerProfile_UserNotFound() throws UserException {
       TrainerUpdateRequestDTO trainerDto = new TrainerUpdateRequestDTO();
       trainerDto.setSpecialization("yoga");
       trainerDto.setUsername("test_username");
       trainerDto.setFirstName("John");
       trainerDto.setLastName("Doe");
       trainerDto.setActive(true);
       when(userRepo.findByUserName(any())).thenReturn(Optional.empty());
      assertThrows(UserException.class,()->trainerService.updateTrainerProfile(trainerDto));
   }
	@Test
    void testUpdateTrainerProfile_NotFound() throws TrainerException {
       TrainerUpdateRequestDTO trainerDto = new TrainerUpdateRequestDTO();
       trainerDto.setSpecialization("yoga");
       trainerDto.setUsername("test_username");
       trainerDto.setFirstName("John");
       trainerDto.setLastName("Doe");
       trainerDto.setActive(true);
       when(userRepo.findByUserName(any())).thenReturn(Optional.of(user));
       when(trainerRepo.findByUserId(anyInt())).thenReturn(Optional.empty());
      assertThrows(TrainerException.class,()->trainerService.updateTrainerProfile(trainerDto));
   }
	
}
