//package com.epam.gymapplication.restcontroller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.epam.gymapplication.dtos.ChangeCredentials;
//import com.epam.gymapplication.dtos.CredentialsDTO;
//import com.epam.gymapplication.exceptions.TrainerException;
//import com.epam.gymapplication.service.UserServiceImpl;
//
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//
//@RestController()
//@RequestMapping("/gym/user")
//@Slf4j
//public class UserController {
//
//	@Autowired
//	UserServiceImpl userService;
//
//	@PostMapping("/login")
//	@ResponseStatus(HttpStatus.OK)
//    public void login(@RequestBody @Valid CredentialsDTO credentials) {
//		log.info("credentials received for login");
//        userService.validateLogin(credentials);
//    }
//
//	@PutMapping("/changeLogin")
//	@ResponseStatus(HttpStatus.OK)
//	public void changeCredentials(@RequestBody @Valid ChangeCredentials credentials) throws TrainerException{
//		log.info("updated credentials received from user");
//		userService.changeLogin(credentials);
//	}
//
//
//
//}
