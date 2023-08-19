package com.epam.gymapplication.restcontroller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.epam.gymapplication.dtos.CredentialsDTO;
import com.epam.gymapplication.dtos.TrainerDTO;
import com.epam.gymapplication.dtos.TrainerProfileDTO;
import com.epam.gymapplication.dtos.TrainerUpdateRequestDTO;
import com.epam.gymapplication.service.TrainerServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gym/trainer")
@Slf4j
public class TrainerController {
	@Autowired
	TrainerServiceImpl trainerService;
	
	@PostMapping("register")
	public ResponseEntity<CredentialsDTO> register(@RequestBody @Valid TrainerDTO trainerDto){
		log.info("trainer details fetched to register");
		return new ResponseEntity<>(trainerService.registerTrainer(trainerDto),HttpStatus.CREATED);
	}
	
	@GetMapping("{username}")
	public ResponseEntity<TrainerProfileDTO> getTrainer(@PathVariable @Valid String username){
		log.info("trainer username received to obtain trainer details");
		return new ResponseEntity<>(trainerService.getTrainerProfile(username),HttpStatus.OK);
	}
	
	@PutMapping()
	public ResponseEntity<TrainerProfileDTO> updateTrainee(@RequestBody @Valid TrainerUpdateRequestDTO trainer){
		log.info("updated trainer details received");
		return new ResponseEntity<>(trainerService.updateTrainerProfile(trainer),HttpStatus.OK);
	}	

//	@GetMapping("/reports")
//	public ResponseEntity<List<ReportResponseDTO>> getTrainingReports(@RequestParam LocalDate date) {
//		return new ResponseEntity<>(trainerService.getAllTrainingsDetails(date),HttpStatus.OK);
//	}
}
