package com.epam.notification.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.epam.dto.MailRequestDTO;
import com.epam.notification.entity.EmailSummary;
import com.epam.notification.repository.NotificationRepo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService{
	
	@Autowired
	JavaMailSender mailSender;
	@Autowired
	NotificationRepo notificationRepo;
	@Value("${spring.mail.username}")
	String from;

	@Override
	@KafkaListener(topics="notification-topic",groupId="myGroup")
	public void sendMail(MailRequestDTO user) throws MessagingException {
		log.info("about to send mail");
		
		String[] to = user.getToMails().toArray(new String[0]);
		SimpleMailMessage helper = new SimpleMailMessage();
		 
		helper.setSubject(user.getDetails().get("subject"));
		helper.setFrom(from);
		helper.setTo(to);
		helper.setCc(user.getCcMails().get(0));
		String body = user.getDetails().entrySet().stream()
				.filter(entry -> !entry.getKey().equals("subject"))
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n"));
		helper.setText(body);
		    EmailSummary email=EmailSummary.builder().fromEmail(from).toEmails(List.of(to)).ccEmails(List.of(to)).build();
		    
		    try {
		    mailSender.send(helper);
		    email.setStatus("SENT");
		    email.setRemarks("mail sent successfully");
		    log.info("mail sending success");
		    }
		    catch(MailException e) {
		    	email.setStatus("FAILED");
		    	email.setRemarks(e.getMessage());
		    	log.error("mail sending failed");
		    }
		    notificationRepo.save(email);
		    log.info("exiting sendMail()");
	}
}

//	@Override
//	public void sendMailAfterUpdatingProfile(MailRequestDTO user) {
//		String from = "bhavanasample@gmail.com";
//		String to = user.getEmail();
//		SimpleMailMessage message = new SimpleMailMessage(); 
//		message.setFrom(from);
//		message.setTo(to);
//		message.setSubject("Update on your profile");
//		message.setText("Hi"+ user.getFirstName()+"!"
//				+"Your profile has updated"); 
//		mailSender.send(message);
//		EmailSummary email=EmailSummary.builder().fromEmail(from).toEmails(List.of(to)).ccEmails(List.of(to))
//			    .status("Sent").remarks("sent successfully").build();
//		notificationRepo.save(email);
//		
//	}
//
//	@Override
//	public void sendMailAfterAddingTraining(TrainingDTO training) throws MessagingException {
//		String from = "bhavanasample@gmail.com";
//		List<String> toMails=new ArrayList<>(); 
//		MimeMessage message = mailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message); 
//		helper.setSubject("Your Training added Successfully");
//		helper.setFrom(from);
//		String toTrainee=training.getTraineeEmail();
//		helper.setTo(toTrainee);
//		toMails.add(toTrainee);
//		 String htmlContentForTrainee = "<html><body>"
//	                + "<h1>Hi " + training.getTraineeName() + "!</h1>"
//	                + "<p>Your training details:</p>"
//	                + "<p><b>Training Name:</b> " + training.getTrainingName() + "</p>"
//	                + "<p><b>Training Type:</b> " + training.getTrainingType() + "</p>"
//	                + "<p><b>Duration:</b> " + training.getDuration() + " hour/hours</p>"
//	                + "<p>Your trainer: " + training.getTrainerName() + " (" + training.getTrainerEmail() + ")</p>"
//	                + "</body></html>";
//	        helper.setText(htmlContentForTrainee, true);
//	        mailSender.send(message);
//	        String toTrainer=training.getTrainerEmail();
//	        helper.setTo(toTrainer);
//	        toMails.add(toTrainer);
//	        String htmlContentForTrainer = "<html><body>"
//	                + "<h1>Hi " + training.getTrainerName() + "!</h1>"
//	                + "<p>Your training details:</p>"
//	                + "<p><b>Training Name:</b> " + training.getTrainingName() + "</p>"
//	                + "<p><b>Training Type:</b> " + training.getTrainingType() + "</p>"
//	                + "<p><b>Duration:</b> " + training.getDuration() + " hour/hours</p>"
//	                + "<p>Your trainee: " + training.getTraineeName() + " (" + training.getTraineeEmail() + ")</p>"
//	                + "</body></html>";
//	        helper.setText(htmlContentForTrainer, true);
//
//	        mailSender.send(message);
//	        
//	        EmailSummary email=EmailSummary.builder().fromEmail(from).toEmails(toMails).ccEmails(toMails)
//	    		    .status("Sent").remarks("sent successfully").build();
//	    		    notificationRepo.save(email);
//	    }
    







		
	
	
	
	
	


