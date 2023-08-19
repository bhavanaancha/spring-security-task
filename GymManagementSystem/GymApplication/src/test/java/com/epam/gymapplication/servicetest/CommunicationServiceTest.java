package com.epam.gymapplication.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import com.epam.dto.MailRequestDTO;
import com.epam.dto.ReportRequestDTO;
import com.epam.gymapplication.service.CommunicationService;

@ExtendWith(MockitoExtension.class)
//@EnableAutoConfiguration(exclude = KafkaAutoConfiguration.class)
 class CommunicationServiceTest {
	
	@InjectMocks
    private CommunicationService communicationService;

    @Mock
    private KafkaTemplate<String, MailRequestDTO> kafkaMailTemplate;

    @Mock
    private KafkaTemplate<String, ReportRequestDTO> kafkaReportTemplate;

    @Test
     void testSendNotification() {
    	 MailRequestDTO notification = new MailRequestDTO();
    	 Message<MailRequestDTO> message = MessageBuilder
    	            .withPayload(notification)
    	            .setHeader(MessageHeaders.CONTENT_TYPE, "application/json")
    	            .build();
    	    communicationService.sendNotification(notification);

    }

    @Test
     void testSendTrainingReport() {
        ReportRequestDTO trainingSummary = new ReportRequestDTO();
        Message<ReportRequestDTO> message = MessageBuilder
	            .withPayload(trainingSummary)
	            .setHeader(MessageHeaders.CONTENT_TYPE, "application/json")
	            .build();
        communicationService.sendTrainingReport(trainingSummary);
      
    }

}
