package com.epam.gymapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.epam.dto.MailRequestDTO;
import com.epam.dto.ReportRequestDTO;

@Service
public class CommunicationService {

	private final KafkaTemplate<String, MailRequestDTO> kafkaMailTemplate;
    private final KafkaTemplate<String, ReportRequestDTO> kafkaReportTemplate;

    @Autowired
    public CommunicationService(
            KafkaTemplate<String, MailRequestDTO> kafkaMailTemplate,
            KafkaTemplate<String, ReportRequestDTO> kafkaReportTemplate
    ) {
        this.kafkaMailTemplate = kafkaMailTemplate;
        this.kafkaReportTemplate = kafkaReportTemplate;
    }

    public void sendNotification(MailRequestDTO notification) {
        Message<MailRequestDTO> message = MessageBuilder.withPayload(notification)
                                                        .setHeader(KafkaHeaders.TOPIC, "notification-topic")
                                                        .build();
        kafkaMailTemplate.send(message);
    }

    public void sendTrainingReport(ReportRequestDTO trainingSummary) {
        Message<ReportRequestDTO> trainingMessage = MessageBuilder.withPayload(trainingSummary)
                                                                  .setHeader(KafkaHeaders.TOPIC, "topic-report")
                                                                  .build();
        kafkaReportTemplate.send(trainingMessage);
    }

}
