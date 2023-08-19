package com.epam.gymapplication.utilitytest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.gymapplication.utility.CredentialGenerator;

@ExtendWith(MockitoExtension.class)
 class CredentialsGeneratorTest {
	
	@InjectMocks
    private CredentialGenerator credentialGenerator;

    @Test
    void testGenerateRandomPassword() {
        int length = 10;
      assertNotNull(credentialGenerator.generateRandomPassword(length));
    }
    @Test
     void testValidPassword() {
        String validPassword = "Abcd1234";

        boolean isValid = credentialGenerator.isPasswordValid(validPassword);

        assertTrue(isValid);
    }

    @Test
     void testInvalidPassword() {
        String invalidPassword = "abcd";

        boolean isValid = credentialGenerator.isPasswordValid(invalidPassword);

        assertFalse(isValid);

    }

}
