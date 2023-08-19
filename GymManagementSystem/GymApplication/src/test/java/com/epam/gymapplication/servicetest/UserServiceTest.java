package com.epam.gymapplication.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.gymapplication.dtos.ChangeCredentials;
import com.epam.gymapplication.dtos.CredentialsDTO;
import com.epam.gymapplication.entity.User;
import com.epam.gymapplication.exceptions.UserException;
import com.epam.gymapplication.repository.UserRepo;
import com.epam.gymapplication.service.UserServiceImpl;
import com.epam.gymapplication.utility.CredentialGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Mock
	UserRepo userRepo;
	@Mock
	CredentialGenerator generator;
	@Mock
	PasswordEncoder passwordEncoder;
	@InjectMocks
	UserServiceImpl userService;
	
	@Test
     void testValidateLogin_ValidCredentials() {
		String userName = "testUser";
		String password = "testPassword";
		User user = new User();
		user.setUserName(userName);
		user.setPassword(passwordEncoder.encode(password));
		when(userRepo.findByUserName(userName)).thenReturn(Optional.of(user));
		when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
		CredentialsDTO credentials = new CredentialsDTO(userName, password);
		boolean isValid=userService.validateLogin(credentials);
		assertEquals(true,isValid);
    }
	
	 @Test
	    void testValidateLogin_InvalidCredentials() {
	        CredentialsDTO credentials = new CredentialsDTO();
	        credentials.setUserName("test_username");
	        credentials.setPassword("test_password");
	        when(userRepo.findByUserName(credentials.getUserName()))
	                .thenReturn(Optional.empty());
	       assertThrows(UserException.class,()-> userService.validateLogin(credentials));
	       
	    }

	    @Test
	     void testChangeLogin_ValidCredentials() throws UserException {
	        ChangeCredentials credentials = new ChangeCredentials();
	        credentials.setUsername("test_username");
	        credentials.setOldPassword("old_password");
	        credentials.setNewPassword("new_password");
	        User user = new User();
	        user.setPassword("old_password");
	        when(userRepo.findByUserName(credentials.getUsername())).thenReturn(Optional.of(user));
	        when(generator.isPasswordValid(credentials.getNewPassword())).thenReturn(true);
	        boolean result = userService.changeLogin(credentials);
	        verify(userRepo).findByUserName(credentials.getUsername());
	        verify(generator).isPasswordValid(credentials.getNewPassword());
	        assertTrue(result);
	        assertEquals(credentials.getNewPassword(), user.getPassword());
	    }
	    @Test
	     void testChangeLogin_InvalidUsername() throws UserException {
	        ChangeCredentials credentials = new ChangeCredentials();
	        credentials.setUsername("test_username");
	        credentials.setOldPassword("old_password");
	        credentials.setNewPassword("new_password");
	        User user = new User();
	        user.setPassword("different_password");
	        when(userRepo.findByUserName(credentials.getUsername())).thenReturn(Optional.empty());
	        assertThrows(UserException.class,()->userService.changeLogin(credentials));
	        
	    }

	    @Test
	     void testChangeLogin_InvalidOldPassword() throws UserException {
	        ChangeCredentials credentials = new ChangeCredentials();
	        credentials.setUsername("test_username");
	        credentials.setOldPassword("old_password");
	        credentials.setNewPassword("new_password");
	        User user = new User();
	        user.setPassword("different_password");
	        when(userRepo.findByUserName(credentials.getUsername())).thenReturn(Optional.of(user));
	        assertThrows(UserException.class,()->userService.changeLogin(credentials));
	        
	    }

	    @Test
	     void testChangeLogin_InvalidNewPassword() throws UserException {
	        ChangeCredentials credentials = new ChangeCredentials();
	        credentials.setUsername("test_username");
	        credentials.setOldPassword("old_password");
	        credentials.setNewPassword("weakpassword");
	        User user = new User();
	        user.setPassword("old_password");
	        when(userRepo.findByUserName(credentials.getUsername())).thenReturn(Optional.of(user));
	        when(generator.isPasswordValid(credentials.getNewPassword())).thenReturn(false);
	        assertThrows(UserException.class,()->userService.changeLogin(credentials));
	    }
	}
	








