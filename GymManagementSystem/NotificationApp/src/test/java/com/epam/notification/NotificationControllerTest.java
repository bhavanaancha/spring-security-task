//package com.epam.notification;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.epam.dto.MailRequestDTO;
//import com.epam.notification.restcontroller.NotificationController;
//import com.epam.notification.service.NotificationServiceImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@WebMvcTest(NotificationController.class)
//@AutoConfigureMockMvc(addFilters = false)
// class NotificationControllerTest {
//	@Autowired
//	MockMvc mockMvc;
//	@MockBean
//	NotificationServiceImpl notificationService;
//	
//	@Test
//	void testSendMail() throws Exception {
//		 MailRequestDTO reportRequest = new MailRequestDTO();
//		    reportRequest.setCcMails(List.of("a@gmail.com"));
//		    reportRequest.setToMails(List.of("a@gmail.com"));
//		notificationService.sendMail(reportRequest);
//		mockMvc.perform(post("/notifications").contentType(MediaType.APPLICATION_JSON)
//				.content(new ObjectMapper().writeValueAsString(reportRequest))).andExpect(status().isCreated());
//	}
//	
//
//}
