package com.epam.reportingservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.epam.reportingservice.entity.TrainingsSummary;

public interface ReportingRepo extends MongoRepository<TrainingsSummary, String>{

}
