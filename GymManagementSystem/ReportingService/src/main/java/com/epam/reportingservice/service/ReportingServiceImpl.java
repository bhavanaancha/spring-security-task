package com.epam.reportingservice.service;


import java.time.LocalDate;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.epam.dto.ReportRequestDTO;
import com.epam.reportingservice.dtos.ReportResponseDTO;
import com.epam.reportingservice.entity.TrainingsSummary;
import com.epam.reportingservice.exceptions.ReportException;
import com.epam.reportingservice.repository.ReportingRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReportingServiceImpl implements ReportingService{
	
	@Autowired
	ReportingRepo reportRepo;

	@Override
	@KafkaListener(topics="topic-report",groupId="reportGroup")
	public void saveReport(ReportRequestDTO report) {
		log.info("entered into save report");
		log.info(report.getUserName()+" "+report.getFirstName());
		TrainingsSummary summary = reportRepo.findById(report.getUserName())
	            .orElseGet(() -> {
	                return TrainingsSummary.builder()
	                        .firstName(report.getFirstName())
	                        .lastName(report.getLastName())
	                        .username(report.getUserName())
	                        .status(report.isStatus())
	                        .summary(new HashMap<>()) 
	                        .build();
	            });

	    LocalDate date = report.getTrainingDate();
	    int year=date.getYear();
	    int month = date.getMonthValue();
	    int day = date.getDayOfMonth();
	    summary.getSummary()
	    		.computeIfAbsent(year,y-> new HashMap<>())
	            .computeIfAbsent(month, m -> new HashMap<>())
	            .put(day, report.getDuration());
	    
	    reportRepo.save(summary);
	    log.info("report saved");
	}


	@Override
	public ReportResponseDTO getReport(String userName) {
		TrainingsSummary report=reportRepo.findById(userName)
				.orElseThrow(()->new ReportException("trainer not found with this username"));
		
		return ReportResponseDTO.builder().duration(report.getSummary()).firstName(report.getFirstName())
				.lastName(report.getLastName()).username(report.getUsername())
				.isActive(report.isStatus()).build();
	}        
	
	
}
	