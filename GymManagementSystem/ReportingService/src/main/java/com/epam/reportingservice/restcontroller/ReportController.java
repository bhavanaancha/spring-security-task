package com.epam.reportingservice.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.ReportRequestDTO;
import com.epam.reportingservice.dtos.ReportResponseDTO;
import com.epam.reportingservice.service.ReportingServiceImpl;

@RestController
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
	ReportingServiceImpl reportingService;
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	void saveReport(@RequestBody ReportRequestDTO report) {
		reportingService.saveReport(report);
	}
	
	@GetMapping()
	ResponseEntity<ReportResponseDTO> getReport(@RequestParam String username) {
		return new ResponseEntity<>(reportingService.getReport(username),HttpStatus.OK);
	}

}
