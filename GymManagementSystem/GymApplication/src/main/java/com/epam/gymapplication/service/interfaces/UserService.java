package com.epam.gymapplication.service.interfaces;

import com.epam.gymapplication.dtos.ChangeCredentials;
import com.epam.gymapplication.dtos.CredentialsDTO;
import com.epam.gymapplication.exceptions.UserException;

public interface UserService {
	boolean validateLogin(CredentialsDTO credentials);
	boolean changeLogin(ChangeCredentials credentials) throws UserException;
}
