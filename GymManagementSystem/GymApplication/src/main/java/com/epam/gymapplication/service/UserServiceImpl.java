package com.epam.gymapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.epam.gymapplication.dtos.ChangeCredentials;
import com.epam.gymapplication.dtos.CredentialsDTO;
import com.epam.gymapplication.entity.User;
import com.epam.gymapplication.exceptions.UserException;
import com.epam.gymapplication.repository.UserRepo;
import com.epam.gymapplication.service.interfaces.UserService;
import com.epam.gymapplication.utility.CredentialGenerator;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepo userRepo;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	CredentialGenerator generator;
	
	@Override
	public boolean validateLogin(CredentialsDTO credentials) {
		boolean isValid = userRepo.findByUserName(credentials.getUserName())
				.filter(user -> encoder.matches(credentials.getPassword(), user.getPassword()))
				.isPresent();
		if (!isValid) {
			throw new UserException("Invalid credentials");
		}
		return true;
	}

	@Override
	@Transactional
	public boolean changeLogin(ChangeCredentials credentials) throws UserException {
		
		User user=userRepo.findByUserName(credentials.getUsername())
		.orElseThrow(()->new UserException("Invalid username"));
		if(!user.getPassword().equals(credentials.getOldPassword())){
			throw new UserException("Invalid old password");
		}
		if(!generator.isPasswordValid(credentials.getNewPassword())) {
			throw new UserException("Create strong password containing atleast one uppercase letter and a number");
		}
		user.setPassword(credentials.getNewPassword());
		return true;

	}

}
