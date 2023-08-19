package com.epam.notification.service;

import com.epam.dto.MailRequestDTO;

import jakarta.mail.MessagingException;

public interface NotificationService {
	void sendMail(MailRequestDTO user) throws MessagingException;
	

}
