package com.epam.gymapplication.controllertest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

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
import com.epam.gymapplication.dtos.TraineeRequestDTO;
import com.epam.gymapplication.dtos.TraineeUpdateRequestDTO;
import com.epam.gymapplication.dtos.TrainerResponseDTO;
import com.epam.gymapplication.entity.Trainee;
import com.epam.gymapplication.entity.User;
import com.epam.gymapplication.repository.TraineeRepo;
import com.epam.gymapplication.restcontroller.TraineeController;
import com.epam.gymapplication.service.TraineeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(TraineeController.class)
@AutoConfigureMockMvc(addFilters = false)
 class TraineeControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TraineeRepo traineeRepo;
	@MockBean
	private TraineeServiceImpl traineeService;
	
	Trainee trainee;
	User user;
	TraineeRequestDTO traineeDto;
	
	@BeforeEach
	void setUserAndTrainee() {
		 user=User.builder().email("Sample@gmail.com").firstName("bhavna").lastName("ancha").email("Sample@gmail.com").isActive(true)
				.userName("Sample@gmail.com").password("something").build();
		user.setRegistrationDate(java.time.LocalDate.parse("2001-07-31"));
		traineeDto=TraineeRequestDTO.builder().firstName(user.getFirstName()).lastName(user.getLastName())
				.dateOfBirth(user.getRegistrationDate()).address("mumbai").email(user.getUserName()).isActive(true).build();
		 trainee=Trainee.builder().user(user).address("guntur").dateOfBirth(LocalDate.now()).build();
	}
	
	@Test
	void testRegisterTrainee() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
		CredentialsDTO credentials=CredentialsDTO.builder().userName(user.getUserName()).password(user.getPassword()).build();
		Mockito.when(traineeService.registerTrainee(traineeDto)).thenReturn(credentials);
		mockMvc.perform(post("/gym/trainee/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(traineeDto))).andExpect(status().isCreated());
	}
	
	@Test
	void testGetTrainee() throws Exception {
		TraineeProfileDTO profile=new TraineeProfileDTO();
		Mockito.when(traineeService.getTrainee(any())).thenReturn(profile);
		mockMvc.perform(get("/gym/trainee/Sample@gmail.com")).andExpect(status().isOk());
	}
	
	@Test
	void testUpdateTrainee() throws Exception {
		TraineeUpdateRequestDTO request=new TraineeUpdateRequestDTO();
		request.setActive(true);
		request.setAddress(traineeDto.getAddress());
		request.setDateOfBirth(user.getRegistrationDate());
		request.setEmail(user.getEmail());
		request.setFirstName(user.getFirstName());
		request.setLastName(user.getLastName());
		request.setUsername(user.getUserName());
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
		TraineeProfileDTO profile=new TraineeProfileDTO();
		Mockito.when(traineeService.updateTraineeProfile(any())).thenReturn(profile);
		mockMvc.perform(put("/gym/trainee").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk());
	}
	
	@Test
	void testDeleteTrainee() throws Exception {
		
		traineeService.deleteTraineeProfile(user.getUserName());
		mockMvc.perform(delete("/gym/trainee/Sample@gmail.com")).andExpect(status().isNoContent());
	}
	
	@Test
	void testGetUnassignedTrainee() throws Exception {
		TrainerResponseDTO profiles=new TrainerResponseDTO();
		Mockito.when(traineeService.unassignedTrainers(any())).thenReturn(List.of(profiles));
		mockMvc.perform(get("/gym/trainee/unassigned/Sample@gmail.com")).andExpect(status().isOk());
	}
	@Test
	void testAssociateTrainersToTrainee() throws Exception {
		TrainerResponseDTO profiles=new TrainerResponseDTO();
		Mockito.when(traineeService.updateTraineeTrainers(anyString(),anyList())).thenReturn(List.of(profiles));
		mockMvc.perform(put("/gym/trainee/trainers/Sample@gmail.com").contentType(MediaType.APPLICATION_JSON)
		.content(new ObjectMapper().writeValueAsString(List.of("a@gmail.com")))).andExpect(status().isOk());
	}
	
	
}
