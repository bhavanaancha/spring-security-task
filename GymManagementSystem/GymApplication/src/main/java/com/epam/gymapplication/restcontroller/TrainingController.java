package com.epam.gymapplication.restcontroller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.gymapplication.dtos.TraineeTrainingRequest;
import com.epam.gymapplication.dtos.TraineeTrainingResponseDTO;
import com.epam.gymapplication.dtos.TrainerResponseTrainingDTO;
import com.epam.gymapplication.dtos.TrainerTrainingRequestDTO;
import com.epam.gymapplication.dtos.TrainingDTO;
import com.epam.gymapplication.exceptions.TraineeException;
import com.epam.gymapplication.service.TrainingServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gym/training")
@Slf4j
public class TrainingController {
	@Autowired
	TrainingServiceImpl trainingService;
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void addTraining(@RequestBody @Valid TrainingDTO training) throws TraineeException{
		log.info("details of training fetched to add");
		trainingService.addTraining(training);
	}
	
	@GetMapping("/trainer/{username}")
    public ResponseEntity<List<TrainerResponseTrainingDTO>> getTrainerTrainingsList(
            @PathVariable String username,
            @RequestParam(required = false) @Valid LocalDate periodFrom,
            @RequestParam(required = false)  @Valid LocalDate periodTo,
            @RequestParam(required = false) @Valid String traineeName){
		log.info("trainer username received to fetch training details");
        TrainerTrainingRequestDTO training=TrainerTrainingRequestDTO.builder()
        		.periodFrom(periodFrom).periodTo(periodTo).traineeName(traineeName)
        		.username(username).build();
        List<TrainerResponseTrainingDTO> trainings = trainingService.getTrainerTrainingDetails(training);
        return ResponseEntity.ok(trainings);
}
	@GetMapping("/trainee/{username}")
    public ResponseEntity<List<TraineeTrainingResponseDTO>> getTraineeTrainingsList(
            @PathVariable String username,
            @RequestParam(required = false) @Valid LocalDate periodFrom,
            @RequestParam(required = false) @Valid LocalDate periodTo,
            @RequestParam(required = false) String trainerName,
            @RequestParam(required = false) String trainingType) throws TraineeException {
		log.info("trainee username received to fetch training details");
		TraineeTrainingRequest training=TraineeTrainingRequest.builder()
        		.periodFrom(periodFrom).periodTo(periodTo).trainerName(trainerName)
        		.username(username).trainingType(trainingType).build();
      
        List<TraineeTrainingResponseDTO> trainings = trainingService.getTraineeTrainingDetails(training);
        return ResponseEntity.ok(trainings);
}
	

}
