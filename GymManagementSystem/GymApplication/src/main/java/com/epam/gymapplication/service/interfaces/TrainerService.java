package com.epam.gymapplication.service.interfaces;

import com.epam.gymapplication.dtos.CredentialsDTO;
import com.epam.gymapplication.dtos.TrainerDTO;
import com.epam.gymapplication.dtos.TrainerProfileDTO;
import com.epam.gymapplication.dtos.TrainerUpdateRequestDTO;

public interface TrainerService {
	
	CredentialsDTO registerTrainer(TrainerDTO trainerDto);
	TrainerProfileDTO getTrainerProfile(String username);
	TrainerProfileDTO updateTrainerProfile(TrainerUpdateRequestDTO trainerDto);
	
	
}
