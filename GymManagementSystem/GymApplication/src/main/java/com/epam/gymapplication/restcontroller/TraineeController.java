package com.epam.gymapplication.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.gymapplication.dtos.CredentialsDTO;
import com.epam.gymapplication.dtos.TraineeProfileDTO;
import com.epam.gymapplication.dtos.TraineeRequestDTO;
import com.epam.gymapplication.dtos.TraineeUpdateRequestDTO;
import com.epam.gymapplication.dtos.TrainerResponseDTO;
import com.epam.gymapplication.exceptions.TraineeException;
import com.epam.gymapplication.service.TraineeServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gym/trainee")
@Slf4j
public class TraineeController {
	
	@Autowired
	TraineeServiceImpl traineeService;
	
	@PostMapping("register")
	public ResponseEntity<CredentialsDTO> register(@RequestBody @Valid TraineeRequestDTO traineeDto){
		log.info("entered into controller method for traibee registration");
		return new ResponseEntity<>(traineeService.registerTrainee(traineeDto),HttpStatus.CREATED);
	}
	
	@GetMapping("{username}")
	public ResponseEntity<TraineeProfileDTO> getTrainee(@PathVariable @Valid String username) throws TraineeException{
		log.info("username fetched for getting trainee profile");
		return new ResponseEntity<>(traineeService.getTrainee(username),HttpStatus.OK);
	}
	
	@PutMapping()
	public ResponseEntity<TraineeProfileDTO> updateTrainee(@RequestBody @Valid TraineeUpdateRequestDTO trainee) throws TraineeException{
		log.info("trainee details received for updating");
		return new ResponseEntity<>(traineeService.updateTraineeProfile(trainee),HttpStatus.OK);
	}
	
	@DeleteMapping("/{username}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTrainee(@PathVariable @Valid String username) throws TraineeException {
		log.info("trainee username received for deletion");
		traineeService.deleteTraineeProfile(username);
	}
	
	@GetMapping("/unassigned/{username}")
	public ResponseEntity<List<TrainerResponseDTO>> getUnassignedTrainers(@PathVariable @Valid String username) throws TraineeException{
		log.info("trainee username received for obtaining unassigned trainers");
		return new ResponseEntity<>(traineeService.unassignedTrainers(username),HttpStatus.OK);
	}
	@PutMapping("/trainers/{username}")
	public ResponseEntity<List<TrainerResponseDTO>> mapTrainersToTrainees(@PathVariable @Valid String username,
			@RequestBody  List<String> trainerUserNames) throws TraineeException{
		log.info("trainee and trainers details to be mapped received");
		return new ResponseEntity<>(traineeService.updateTraineeTrainers(username, trainerUserNames),HttpStatus.OK);
	}
	
	
	 
}
