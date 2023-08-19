package com.epam.gymapplication.utility;


import java.util.Random;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CredentialGenerator {
    public  String generateRandomPassword(int length) {
    	log.info("Generating a random password of length: {}", length);
    	return new Random().ints(10, 48, 123).filter(i -> (i <= 57 || (i >= 65 && i <= 90) || (i >= 97 && i <= 122)))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
    
    
    public  boolean isPasswordValid(String password) {
    	 log.info("Checking if the password is valid");
        int uppercaseCount = 0;
        int numberCount = 0;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                uppercaseCount++;
            } else if (Character.isDigit(ch)) {
                numberCount++;
            }
        }
        log.info("Password validity check result");
        return !(uppercaseCount == 0 || numberCount == 0);
    }

}
