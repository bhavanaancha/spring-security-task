package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.AuthRequest;
import com.epam.controller.AuthController;
import com.epam.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = AuthControllerTest.Config.class) 
 class AuthControllerTest {
	@Autowired
	MockMvc mockMvc;
	@MockBean
	AuthService authService;
	@MockBean
	AuthController authcontroller;
	@MockBean
	AuthenticationManager authenticationManager;
	
	 @Test
	    void testGetTokenSuccess() throws Exception {
	        AuthRequest authRequest = new AuthRequest("username", "password");
	        Authentication authentication = mock(Authentication.class);
	        when(authenticationManager.authenticate(any())).thenReturn(authentication);
	        when(authentication.isAuthenticated()).thenReturn(true);
	        when(authService.generateToken("username")).thenReturn("generated-token");
	        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(authRequest)))
	        .andExpect(content().string("generated-token"));
	    }
	 @Test
	    void testGetInvalidTokenSuccess() throws Exception {
	        AuthRequest authRequest = new AuthRequest("username", "password");
	        Authentication authentication = mock(Authentication.class);
	        when(authenticationManager.authenticate(any())).thenReturn(authentication);
	        when(authentication.isAuthenticated()).thenReturn(false);
	        assertThrows(RuntimeException.class, ()->authcontroller.getToken(authRequest));
			mockMvc.perform(post("/auth/login").content(new ObjectMapper().writeValueAsString(authRequest)));
	    }
	 	
	 @Test
	    void testValidateToken() throws Exception {
	        String token = "valid-token";
	        authService.validateToken(token);
	        mockMvc.perform(get("/auth/validate").param("token", token)).andExpect(content().string("Token is valid"));
	       
	    }
	 @Configuration
	    @Import(AuthController.class) // Import the class you want to test
	    static class Config {
	    }
}
