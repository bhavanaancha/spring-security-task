package com.epam.gymapplication.controllertest;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.gymapplication.dtos.CredentialsDTO;
import com.epam.gymapplication.dtos.TraineeProfileDTO;
import com.epam.gymapplication.dtos.TrainerDTO;
import com.epam.gymapplication.dtos.TrainerProfileDTO;
import com.epam.gymapplication.dtos.TrainerUpdateRequestDTO;
import com.epam.gymapplication.entity.Trainer;
import com.epam.gymapplication.entity.TrainingType;
import com.epam.gymapplication.entity.User;
import com.epam.gymapplication.repository.TrainerRepo;
import com.epam.gymapplication.restcontroller.TrainerController;
import com.epam.gymapplication.service.TrainerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(TrainerController.class)
@AutoConfigureMockMvc(addFilters = false)
 class TrainerControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TrainerRepo trainerRepo;
	@MockBean
	private TrainerServiceImpl trainerService;
	
	Trainer trainer;
	User user;
	
	@BeforeEach
	void setUserAndTrainer() {
		 user=User.builder().email("Sample@gmail.com").firstName("bhavna").lastName("ancha").email("Sample@gmail.com").isActive(true)
				.userName("Sample@gmail.com").password("something").build();
		user.setRegistrationDate(java.time.LocalDate.parse("2001-07-31"));
//		traineeDto=TraineeRequestDTO.builder().firstName(user.getFirstName()).lastName(user.getLastName())
//				.dateOfBirth(user.getRegistrationDate()).address("mumbai").email(user.getUserName()).isActive(true).build();
//		 Trainee trainee=Trainee.builder().user(user).address("guntur").dateOfBirth(LocalDate.now()).build();
		TrainingType type=new TrainingType(); 
		type.setTrainingTypeName("yoga");
		trainer=Trainer.builder().user(user).specializationId(type).build();
	}
	
	@Test
	void testRegisterTrainer() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        TrainerDTO trainerDto=TrainerDTO.builder().email(user.getEmail()).firstName(user.getFirstName())
        		.lastName(user.getLastName()).specialization("yoga").build();
		CredentialsDTO credentials=CredentialsDTO.builder().userName(user.getUserName()).password(user.getPassword()).build();
		Mockito.when(trainerService.registerTrainer(trainerDto)).thenReturn(credentials);
		mockMvc.perform(post("/gym/trainer/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(trainerDto))).andExpect(status().isCreated());
	}
	
	@Test
	void testGetTrainee() throws Exception {
		TrainerProfileDTO profile=new TrainerProfileDTO();
		Mockito.when(trainerService.getTrainerProfile(any())).thenReturn(profile);
		mockMvc.perform(get("/gym/trainer/Sample@gmail.com")).andExpect(status().isOk());
	}
	
	@Test
	void testUpdateTrainer() throws Exception {
		TrainerUpdateRequestDTO request=new TrainerUpdateRequestDTO();
		request.setActive(true);
		request.setFirstName(user.getFirstName());
		request.setLastName(user.getLastName());
		request.setSpecialization("yoga");
		request.setUsername(user.getUserName());
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
		TrainerProfileDTO profile=new TrainerProfileDTO();
		Mockito.when(trainerService.updateTrainerProfile(any())).thenReturn(profile);
		mockMvc.perform(put("/gym/trainer").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk());
	}
}
