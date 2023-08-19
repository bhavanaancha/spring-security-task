package com.epam.notification.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.epam.notification.entity.EmailSummary;

public interface NotificationRepo extends MongoRepository<EmailSummary,Id>{

}
