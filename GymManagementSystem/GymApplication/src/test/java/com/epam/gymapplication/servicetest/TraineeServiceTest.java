package com.epam.gymapplication.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.MailRequestDTO;
import com.epam.gymapplication.dtos.CredentialsDTO;
import com.epam.gymapplication.dtos.TraineeProfileDTO;
import com.epam.gymapplication.dtos.TraineeRequestDTO;
import com.epam.gymapplication.dtos.TraineeUpdateRequestDTO;
import com.epam.gymapplication.dtos.TrainerResponseDTO;
import com.epam.gymapplication.entity.Trainee;
import com.epam.gymapplication.entity.Trainer;
import com.epam.gymapplication.entity.TrainingType;
import com.epam.gymapplication.entity.User;
import com.epam.gymapplication.exceptions.TraineeException;
import com.epam.gymapplication.exceptions.UserException;
import com.epam.gymapplication.repository.TraineeRepo;
import com.epam.gymapplication.repository.TrainerRepo;
import com.epam.gymapplication.repository.TrainingRepo;
import com.epam.gymapplication.repository.TrainingTypeRepo;
import com.epam.gymapplication.repository.UserRepo;
import com.epam.gymapplication.service.CommunicationService;
import com.epam.gymapplication.service.TraineeServiceImpl;
import com.epam.gymapplication.service.TrainerServiceImpl;
import com.epam.gymapplication.utility.ObjectMapper;

@ExtendWith(MockitoExtension.class)
 class TraineeServiceTest {
	
	@Mock
	TraineeRepo traineeRepo;
	@Mock
	TrainerRepo trainerRepo;
	@InjectMocks
	TraineeServiceImpl traineeService;
	@Mock
	UserRepo userRepo;
	@Mock
	ObjectMapper objectMapper;
	@Mock
	CommunicationService communicationService;

	User user;
	Trainer trainer;
	Trainee trainee;
	User user2;
	TrainingType type = new TrainingType();
	
	@BeforeEach
	void setUser() {
		 user=User.builder().email("Sample@gmail.com").firstName("bhavna").lastName("ancha").isActive(true)
				.userName("Sample@gmail.com").password("something").registrationDate(LocalDate.now()).build();
		 user.setId(1);
		 user2=User.builder().email("SampleTrainer@gmail.com").firstName("bhavna").lastName("ancha").email("SampleTrainer@gmail.com").isActive(true)
					.userName("Sample@gmail.com").password("something").build();
		user.setRegistrationDate(java.time.LocalDate.parse("2001-07-31"));
		
		  trainer=Trainer.builder().user(user2).specializationId(type).build();
		  trainee = Trainee.builder().user(user).build();
		  user.setTrainee(trainee);
		  user2.setTrainer(trainer);
		  trainee.setTrainers(List.of(trainer));
	}
	
	@Test
	void registerTrainee() {
		TraineeRequestDTO traineeDto=TraineeRequestDTO.builder().email("Sample@gmail.com").firstName("bhavna").lastName("ancha").isActive(true)
				.address("guntur").build(); 
        Mockito.when(objectMapper.createTraineeUser(traineeDto)).thenReturn(user);
        Mockito.when(userRepo.save(any(User.class))).thenReturn(user);
        Mockito.when(traineeRepo.save(any(Trainee.class))).thenReturn(trainee);
        MailRequestDTO notification = new MailRequestDTO();
        Mockito.when(objectMapper.mapToMailRequestDTO(user,user.getPassword())).thenReturn(notification);
        Mockito.doNothing().when(communicationService).sendNotification(notification);
        CredentialsDTO expectedCredentials = new CredentialsDTO();
        expectedCredentials.setUserName(traineeDto.getEmail());
        expectedCredentials.setPassword(user.getPassword());
        CredentialsDTO credentials = traineeService.registerTrainee(traineeDto);
        assertEquals(expectedCredentials.getUserName(), credentials.getUserName());	
	}
	
//	@Override
//	public void deleteTraineeProfile(String username) throws TraineeException {
//		log.info("Deleting trainee profile for username: {}", username);
//		User user = userRepo.findByUserName(username)
//				.orElseThrow(() -> new UserException("user not found with this username"));
//		Trainee trainee = Optional.ofNullable(user.getTrainee())
//				.orElseThrow(() -> new TraineeException("trainee can't be found with this username"));
//		trainee.getTrainers().forEach(trainer -> trainer.getTrainees().remove(trainee));
//		userRepo.deleteById(user.getId());
//		log.info("Trainee profile deleted successfully for username: {}", username);
//	}
	
	@Test
	void deleteTrainer() throws TraineeException {
		 String username = "alice";

	        Trainee trainee = Trainee.builder()
	                .user(User.builder().userName(username).build())
	                .trainers(new ArrayList<>()) // Set up trainers if needed
	                .build();
	        
//	        when(trainee.getTrainers()).thenReturn(List.of(trainer));
//	        when(userRepo.findByUserName(username)).thenReturn(Optional.of(user));
	        when(traineeRepo.findByUserUserName(username)).thenReturn(Optional.of(trainee));
	
//	        when(trainer.getTrainees()).thenReturn(List.of(trainee));
//	    when(userRepo.findByUserName(username)).thenReturn(Optional.of(user));
	        traineeService.deleteTraineeProfile(username);
	}
	
//	@Test
//     void testDeleteTraineeProfile_UserNotFound() throws Exception {
//		String username = "nonExistentUsername";
//
//        when(userRepo.findByUserName(username)).thenReturn(Optional.empty());
//
//        assertThrows(UserException.class, () -> traineeService.deleteTraineeProfile(username));
//    }
	
	@Test
    void testDeleteTraineeProfile_TraineeNotFound() throws Exception {
		String username = user.getUserName();
//       Mockito.when(userRepo.findByUserName(username)).thenReturn(Optional.of(user));
       Mockito.when(traineeRepo.findByUserUserName(username)).thenReturn(Optional.empty());
       assertThrows(TraineeException.class, () -> traineeService.deleteTraineeProfile(username));
   }
	
	
	@Test
     void testUpdateTraineeProfile_TraineeNotFound() throws Exception {
        TraineeUpdateRequestDTO traineeDto = new TraineeUpdateRequestDTO();

        Mockito.when(traineeRepo.findByUserUserName(any())).thenReturn(Optional.empty());
        
        assertThrows(TraineeException.class,()->traineeService.updateTraineeProfile(traineeDto));
    }
	
	 @Test
	     void testUpdateTraineeProfile_Success() throws Exception {
		 TraineeUpdateRequestDTO traineeDto = new TraineeUpdateRequestDTO();
	        traineeDto.setUsername("testUser");
	        traineeDto.setFirstName("John");
	        traineeDto.setLastName("Doe");
	        traineeDto.setActive(true);
	        traineeDto.setAddress("123 Main St");
	        traineeDto.setDateOfBirth(LocalDate.of(1990, 1, 1));
	        Trainee trainee = new Trainee();
	        trainee.setUser(new User());
	        when(traineeRepo.findByUserUserName("testUser")).thenReturn(Optional.of(trainee));
	        TraineeProfileDTO profile=new TraineeProfileDTO();
	        profile.setActive(true);
	        profile.setAddress(traineeDto.getAddress());
	        profile.setDateOfBirth(traineeDto.getDateOfBirth());
	        profile.setFirstName(traineeDto.getFirstName());
	        Mockito.when(objectMapper.mapToTraineeProfileDTO(trainee)).thenReturn(profile);
	        Mockito.when(traineeService.updateTraineeProfile(traineeDto)).thenReturn(profile);  
	        assertEquals(profile, traineeService.updateTraineeProfile(traineeDto));
	      
	    }
	 
	 @Test
	     void testGetTrainee_Success() throws Exception {
		 TraineeProfileDTO response=new TraineeProfileDTO();
	        response.setFirstName(user.getFirstName());
	        response.setLastName(user.getLastName());
	        Mockito.when(traineeRepo.findByUserUserName(any())).thenReturn(Optional.of(trainee));
	        Mockito.when(objectMapper.mapToTraineeProfileDTO(trainee)).thenReturn(response);
	        assertEquals(response, traineeService.getTrainee(user.getUserName()));
	    }
	 @Test
     void testGetTrainee_NotFound() throws Exception {
        Mockito.when(traineeRepo.findByUserUserName(any())).thenReturn(Optional.empty());
        assertThrows(TraineeException.class,()->traineeService.getTrainee(user.getUserName()));
    }
	 @Test
	     void testUnassignedTrainers_Success() throws TraineeException {
	        String traineeUsername = "testUser";
	        Trainee trainee = new Trainee();
	        Trainer trainer1 = new Trainer();
	        Trainer trainer2 = new Trainer();
	        List<Trainer> trainers = new ArrayList<>();
	        trainers.add(trainer1);
	        trainers.add(trainer2);
	        Mockito.when(traineeRepo.findByUserUserName(traineeUsername)).thenReturn(Optional.of(trainee));
	        Mockito.when(trainerRepo.findByTraineesNotContaining(trainee)).thenReturn(trainers);

	        List<TrainerResponseDTO> expectedTrainerDtos = new ArrayList<>();
	        expectedTrainerDtos.add(new TrainerResponseDTO());
	        expectedTrainerDtos.add(new TrainerResponseDTO());

	        Mockito.when(objectMapper.getTrainersList(trainers)).thenReturn(expectedTrainerDtos);

	        List<TrainerResponseDTO> result = traineeService.unassignedTrainers(traineeUsername);

	        assertEquals(expectedTrainerDtos, result);
	    }
	 @Test
	  void testUnassignedTrainers_TraineeNotFound() throws TraineeException {
	       Mockito.when(traineeRepo.findByUserUserName(any())).thenReturn(Optional.empty());
	        assertThrows(TraineeException.class,()->  traineeService.unassignedTrainers(user.getUserName()));
	    }
	 
	 @Test
	     void testUpdateTraineeTrainers_Success() throws TraineeException {
		 Trainee traineeMock = mock(Trainee.class);
		    List<User> userMocks = createMockUsers(); 
		    List<TrainerResponseDTO> expectedTrainersDetails = createExpectedTrainersDetails(); 
		    when(traineeRepo.findByUserUserName(anyString())).thenReturn(Optional.of(traineeMock));
		    when(userRepo.findAllByUserNameIn(anyList())).thenReturn(userMocks);
		    List<TrainerResponseDTO> result = traineeService.updateTraineeTrainers(
		        user.getUserName(),
		        List.of(user2.getUserName(), user.getUserName())
		    );
		    assertNotNull(result);
		}

		
		private List<User> createMockUsers() {
		    List<User> users = new ArrayList<>();
		    
		    // Mock User 1
		    User user1 = mock(User.class);
		    Trainer trainer1 = mock(Trainer.class);
		    type.setTrainingTypeName("yoga");
		    trainer1.setSpecializationId(type);
		    when(user1.getUserName()).thenReturn("username1");
		    when(user1.getTrainer()).thenReturn(trainer1);
		    when(user1.getTrainer().getSpecializationId()).thenReturn(type);
		    // Mock User 2
		    User user2 = mock(User.class);
		    Trainer trainer2 = mock(Trainer.class);
		    trainer1.setSpecializationId(type);
		    when(user2.getUserName()).thenReturn("username2");
		    when(user2.getTrainer()).thenReturn(trainer2);
		    when(user2.getTrainer().getSpecializationId()).thenReturn(type);
		    // Add mock users to the list
		    users.add(user1);
		    users.add(user2);
		    
		    return users;
		}

		
		private List<TrainerResponseDTO> createExpectedTrainersDetails() {
		    List<TrainerResponseDTO> trainersDetails = new ArrayList<>();
		   trainersDetails.add(new TrainerResponseDTO());
		    return trainersDetails;
		}
		 

}
