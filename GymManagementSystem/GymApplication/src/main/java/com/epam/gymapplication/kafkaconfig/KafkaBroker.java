package com.epam.gymapplication.kafkaconfig;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration

public class KafkaBroker {
	
	@Bean
    public NewTopic notificationTopic(){
        return TopicBuilder.name("notification-topic").build();
    }
	
	@Bean
    public NewTopic reportTopic(){
        return TopicBuilder.name("topic-report").build();
    }
}
