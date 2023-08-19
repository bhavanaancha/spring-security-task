package com.epam.reportingservice;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.dto.ReportRequestDTO;
import com.epam.reportingservice.dtos.ReportResponseDTO;
import com.epam.reportingservice.repository.ReportingRepo;
import com.epam.reportingservice.restcontroller.ReportController;
import com.epam.reportingservice.service.ReportingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ReportController.class)
@AutoConfigureMockMvc(addFilters = false)
 class ReportControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ReportingRepo reportRepo;
	@MockBean
	private ReportingServiceImpl reportService;
	
	@Test
	void testAddReport() throws Exception {
		 ReportRequestDTO reportRequest = new ReportRequestDTO();
		    reportRequest.setDuration(2);
		    reportRequest.setFirstName("hey");
		    reportRequest.setLastName("hello");
		    reportRequest.setStatus(true);
		    reportRequest.setUserName("heyy");
		reportService.saveReport(reportRequest);
		mockMvc.perform(post("/report").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(reportRequest))).andExpect(status().isCreated());
	}
	
	@Test
	void testGetReport() throws Exception {
	
		 ReportResponseDTO reportRequest = new ReportResponseDTO();
		    reportRequest.setActive(true);
		    reportRequest.setFirstName("hey");
		    reportRequest.setLastName("hello");
		    reportRequest.setUsername("heyy");
		Mockito.when(reportService.getReport("heyy")).thenReturn(reportRequest);
		mockMvc.perform(get("/report").param("username", "heyy")).andExpect(status().isOk());
	}

}
