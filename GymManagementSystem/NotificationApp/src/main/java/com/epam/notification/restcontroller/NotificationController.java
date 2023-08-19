package com.epam.notification.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.MailRequestDTO;
import com.epam.notification.service.NotificationServiceImpl;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("notifications")
@Slf4j
public class NotificationController {
	
	@Autowired
	NotificationServiceImpl notificationService;
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void sendMmail(@RequestBody  MailRequestDTO user) throws MessagingException{
		log.info("entered into controller method for user registration ");
		notificationService.sendMail(user);
	}
}
