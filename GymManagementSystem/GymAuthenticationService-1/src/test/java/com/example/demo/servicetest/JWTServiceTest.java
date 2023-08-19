package com.example.demo.servicetest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.security.Key;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.service.JWTService;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.security.Keys;

@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {
	@InjectMocks
    private JWTService jwtService;

    @Mock
    private JwtParserBuilder jwtParserBuilder;

    @Mock
    private JwtParser jwtParser;

    @Mock
    private Keys keys;

    	@Test
    void testGenerateToken() {
        String userName = "testUser";
        JWTService jwtService = spy(JWTService.class);
        String generatedToken = jwtService.generateToken(userName);
        assertNotNull(generatedToken);
    }

//    @Test
//    void testValidateToken() {
//    	String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0VXNlciIsImlhdCI6MTYzMDI3OTY5OCwiZXhwIjoxNjMwMjgwMzE4fQ.b_vXj9Hho3xvU4zDAsZMzZZyYEVaOTFFiP5OXbrLvwA";
//        when(jwtParserBuilder.setSigningKey(any(Key.class))).thenReturn(jwtParserBuilder);
//        when(jwtParserBuilder.build()).thenReturn(jwtParser);
//        when(jwtParser.parseClaimsJws(token)).thenReturn(mock(Jws.class));
//        assertDoesNotThrow(() -> jwtService.validateToken(token));
//    }

}
