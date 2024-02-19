package com.kuber.jobportal.controllers;

import com.kuber.jobportal.models.User;
import com.kuber.jobportal.services.registrationServices.RegistrationServiceImpl;
import com.kuber.jobportal.services.registrationServices.RegistrationServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private RegistrationServices registrationServices;

    public UserController(RegistrationServiceImpl registrationServices) {
        this.registrationServices = registrationServices;
    }
    @PostMapping("/register")
    public ResponseEntity<User> createNewUserInDataBase(@RequestBody User user){
        User createdUser = registrationServices.createUser(user);
        return new ResponseEntity<>(createdUser , HttpStatus.CREATED);
    }
}
