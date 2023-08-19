package com.epam.gymapplication.controllertest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.epam.gymapplication.dtos.TraineeTrainingResponseDTO;
import com.epam.gymapplication.dtos.TrainerResponseTrainingDTO;
import com.epam.gymapplication.dtos.TrainingDTO;
import com.epam.gymapplication.entity.Trainee;
import com.epam.gymapplication.entity.Trainer;
import com.epam.gymapplication.entity.TrainingType;
import com.epam.gymapplication.entity.User;
import com.epam.gymapplication.restcontroller.TrainingController;
import com.epam.gymapplication.service.TrainingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(TrainingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TrainingControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TrainingServiceImpl trainingService;
	
	User user;
	User user2;
	Trainer trainer;
	Trainee trainee;
	@BeforeEach
	void setUserAndTrainer() {
		 user=User.builder().email("Sample@gmail.com").firstName("bhavna").lastName("ancha").email("Sample@gmail.com").isActive(true)
				.userName("Sample@gmail.com").password("something").build();
		 user2=User.builder().email("SampleTrainer@gmail.com").firstName("bhavna").lastName("ancha").email("SampleTrainer@gmail.com").isActive(true)
					.userName("Sample@gmail.com").password("something").build();
		user.setRegistrationDate(java.time.LocalDate.parse("2001-07-31"));
		
		  trainee=Trainee.builder().user(user).address("guntur").dateOfBirth(LocalDate.now()).build();
		TrainingType type=new TrainingType(); 
		type.setTrainingTypeName("yoga");
		trainer=Trainer.builder().user(user).specializationId(type).build();
	}
	
	
	@Test
	void testAddTraining() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        TrainingDTO trainingDto=TrainingDTO.builder().duration(3).traineeName(user.getUserName()).trainerName(user2.getUserName())
        		.trainingName("something").trainingType("yoga").build();
        trainingDto.setTrainingDate(java.time.LocalDate.parse("2001-07-31"));
	
		trainingService.addTraining(trainingDto);
		mockMvc.perform(post("/gym/training").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(trainingDto))).andExpect(status().isCreated());
	}
	
	@Test
    public void testGetTrainerTrainingsList() throws Exception {
        String username = user.getUserName();
        LocalDate periodFrom = LocalDate.now();
        LocalDate periodTo = LocalDate.now().plusDays(7);
        String traineeName = "sampleTraineeName";
        
        List<TrainerResponseTrainingDTO> responseList = new ArrayList<>();
        
        when(trainingService.getTrainerTrainingDetails(any())).thenReturn(responseList);
        
        MockHttpServletRequestBuilder requestBuilder = get("/gym/training/trainer/{username}", username)
                .param("periodFrom", periodFrom.toString())
                .param("periodTo", periodTo.toString())
                .param("traineeName", traineeName);
        
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
              
    }
    
    @Test
     void testGetTraineeTrainingsList() throws Exception {
        String username = user.getUserName();
        LocalDate periodFrom = LocalDate.now();
        LocalDate periodTo = LocalDate.now().plusDays(7);
        String trainerName = "sampleTrainerName";
        String trainingType = "sampleTrainingType";
        
        List<TraineeTrainingResponseDTO> responseList = new ArrayList<>();
        when(trainingService.getTraineeTrainingDetails(any())).thenReturn(responseList);
        
        MockHttpServletRequestBuilder requestBuilder = get("/gym/training/trainee/{username}", username)
                .param("periodFrom", periodFrom.toString())
                .param("periodTo", periodTo.toString())
                .param("trainerName", trainerName)
                .param("trainingType", trainingType);
        
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

    }







	

}
