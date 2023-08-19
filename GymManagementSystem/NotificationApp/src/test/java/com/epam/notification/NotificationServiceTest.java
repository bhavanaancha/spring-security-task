package com.epam.notification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.SelectorResolutionResult;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.epam.dto.MailRequestDTO;
import com.epam.notification.entity.EmailSummary;
import com.epam.notification.repository.NotificationRepo;
import com.epam.notification.service.NotificationServiceImpl;

import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {
	@Mock
    private MailSender mailSender;

    @Mock
    private JavaMailSender javaMailSender;
    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock
    private NotificationRepo notificationRepo;
    
    @Test
     void testSendMailSuccess() throws Exception { 
        MailRequestDTO mailRequestDTO = MailRequestDTO.builder()
                .toMails(List.of("user@example.com"))
                .ccMails(List.of("cc@example.com"))
                .details(Map.of("subject", "Test Subject", "content", "Test Content"))
                .build();
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        notificationService.sendMail(mailRequestDTO);
    }

//    @Test
//    void testSendMailFailed() throws Exception {
//        MailRequestDTO mailRequestDTO = MailRequestDTO.builder()
//                .toMails(List.of("user@example.com"))
//                .ccMails(List.of("cc@example.com"))
//                .details(Map.of("subject", "Test Subject", "content", "Test Content"))
//                .build();
////        SimpleMailMessage helper = new SimpleMailMessage();
//        doThrow(MailException.class).when(mailSender).send(any(SimpleMailMessage.class));
//       assertThrows(MailException.class,()->notificationService.sendMail(mailRequestDTO));
////        assertEquals(SelectorResolutionResult.Status.FAILED.toString(), response.getStatus());
//    }


}
