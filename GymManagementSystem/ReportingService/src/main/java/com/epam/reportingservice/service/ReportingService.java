package com.epam.reportingservice.service;

import com.epam.dto.ReportRequestDTO;
import com.epam.reportingservice.dtos.ReportResponseDTO;

public interface ReportingService {
	void saveReport (ReportRequestDTO report);
	ReportResponseDTO getReport(String userName);
}
