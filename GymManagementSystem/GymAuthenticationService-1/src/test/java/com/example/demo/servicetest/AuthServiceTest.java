package com.example.demo.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.epam.service.AuthService;
import com.epam.service.JWTService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
	
	@InjectMocks
	AuthService authService;
	@Mock
	JWTService jwtService;	
	@BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(authService, "jwtService", jwtService);
    }

    @Test
    void testGenerateToken() {
        String username = "testUser";
        String expectedToken = "test-token";
        when(jwtService.generateToken(username)).thenReturn(expectedToken);
        String generatedToken = authService.generateToken(username);
        assertEquals(expectedToken, generatedToken);
    }
    @Test
    void testValidateToken() {
        String token = "test-token";
        doNothing().when(jwtService).validateToken(token);
        authService.validateToken(token);
        verify(jwtService).validateToken(token);
    }
}


