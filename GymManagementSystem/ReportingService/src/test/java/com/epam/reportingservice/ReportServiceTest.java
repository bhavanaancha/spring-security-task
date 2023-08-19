package com.epam.reportingservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.dto.ReportRequestDTO;
import com.epam.reportingservice.dtos.ReportResponseDTO;
import com.epam.reportingservice.entity.TrainingsSummary;
import com.epam.reportingservice.exceptions.ReportException;
import com.epam.reportingservice.repository.ReportingRepo;
import com.epam.reportingservice.service.ReportingServiceImpl;

@ExtendWith(MockitoExtension.class)
 class ReportServiceTest {
	@InjectMocks
	private ReportingServiceImpl reportService;
	@Mock
	private ReportingRepo reportRepo;
	
	@Test
	 void testSaveReport() {
	    ReportRequestDTO reportRequest = new ReportRequestDTO();
	    reportRequest.setTrainingDate(LocalDate.now());
	    TrainingsSummary trainingsSummary = TrainingsSummary.builder().firstName("hey")
	    		.lastName("hello").status(true).username("something").build();
	    Map<Integer,Map<Integer,Map<Integer,Integer>>>map=new HashMap<>();
	    int day = 12;
	    int duration = 90;
	    Map<Integer, Integer> innermostMap = new HashMap<>();
	    innermostMap.put(day, duration);
	    int month = 8; 
	    Map<Integer, Map<Integer, Integer>> middleMap = new HashMap<>();
	    middleMap.put(month, innermostMap);
	    int year = 2023;
	    map.put(year, middleMap);
	    trainingsSummary.setSummary(map);
	    when(reportRepo.findById(reportRequest.getUserName())).thenReturn(Optional.of(trainingsSummary));
	    reportService.saveReport(reportRequest);
	    verify(reportRepo, times(1)).findById(reportRequest.getUserName());
	    verify(reportRepo, times(1)).save(trainingsSummary);
	}
	@Test
	void testReportNotFound() {
		 ReportRequestDTO report = new ReportRequestDTO();
		 report.setTrainingDate(LocalDate.now());
	        report.setUserName("user123");
	        report.setFirstName("John");
	        report.setLastName("Doe");
	        report.setStatus(true);
	        TrainingsSummary trainingsSummary = TrainingsSummary.builder().firstName("hey")
		    		.lastName("hello").status(true).username("something").build();
	        Map<Integer,Map<Integer,Map<Integer,Integer>>>map=new HashMap<>();
		    int day = 12;
		    int duration = 90;
		    Map<Integer, Integer> innermostMap = new HashMap<>();
		    innermostMap.put(day, duration);
		    int month = 8; 
		    Map<Integer, Map<Integer, Integer>> middleMap = new HashMap<>();
		    middleMap.put(month, innermostMap);
		    int year = 2023;
		    map.put(year, middleMap);
		    trainingsSummary.setSummary(map);
	        when(reportRepo.findById(report.getUserName())).thenReturn(Optional.empty());

	        // Call the method
	        reportService.saveReport(report);
	}
	
	@Test
	 void testGetReportSuccess() {
	    String userName = "sampleUser";

	    TrainingsSummary trainingsSummary = new TrainingsSummary();
	    Map<Integer,Map<Integer,Map<Integer,Integer>>>map=new HashMap<>();
	    int day = 12;
	    int duration = 90;
	    Map<Integer, Integer> innermostMap = new HashMap<>();
	    innermostMap.put(day, duration);
	    int month = 8; 
	    Map<Integer, Map<Integer, Integer>> middleMap = new HashMap<>();
	    middleMap.put(month, innermostMap);
	    int year = 2023;
	    map.put(year, middleMap);
	    trainingsSummary.setSummary(map);
	    when(reportRepo.findById(userName)).thenReturn(Optional.of(trainingsSummary));
	    ReportResponseDTO response = reportService.getReport(userName);
	    verify(reportRepo, times(1)).findById(userName);
	    assertEquals(trainingsSummary.getFirstName(), response.getFirstName());
	    assertEquals(trainingsSummary.getLastName(), response.getLastName());
	    assertEquals(trainingsSummary.getUsername(), response.getUsername());
	    assertEquals(trainingsSummary.isStatus(), response.isActive());
	}

	@Test
	 void testGetReportNotFound() {
	    String userName = "nonExistentUser";

	    when(reportRepo.findById(userName)).thenReturn(Optional.empty());
	    assertThrows(ReportException.class, () -> reportService.getReport(userName));
	    verify(reportRepo, times(1)).findById(userName);
	}
	







}
