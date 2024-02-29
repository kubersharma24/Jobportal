package com.kuber.jobportal.controllers.userController;

import com.kuber.jobportal.models.Dtos.createAccountDTO.CreateAccountDTO;
import com.kuber.jobportal.models.Dtos.loginDTO.LoginRequestDTO;
import com.kuber.jobportal.models.Dtos.loginDTO.loginResponseDTO;
import com.kuber.jobportal.models.User;
import com.kuber.jobportal.services.Validators.UserVAlidatorImpl;
import com.kuber.jobportal.services.Validators.UserValidator;
import com.kuber.jobportal.services.loginServices.LoginService;
import com.kuber.jobportal.services.loginServices.LoginServiceImpl;
import com.kuber.jobportal.services.userregistrationServices.RegistrationServiceImpl;
import com.kuber.jobportal.services.userregistrationServices.RegistrationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.kuber.jobportal.services.Utility.Constants.*;
@CrossOrigin
@RestController
@RequestMapping(USERS )
public class UserController {
    private RegistrationServices registrationServices;
    private LoginService loginService;
    private UserValidator userValidator;

    @Autowired
    public UserController(UserVAlidatorImpl userValidator, RegistrationServiceImpl registrationServices, LoginServiceImpl loginService) {
        this.registrationServices = registrationServices;
        this.loginService = loginService;
        this.userValidator= userValidator;
    }
    @PostMapping(REGISTER_MAPPING)
    public ResponseEntity<?> createNewUserInDataBase(@RequestBody User user){
        CreateAccountDTO  createAccountDTO = new CreateAccountDTO();
        if(userValidator.isUser(user.getEmail())){
            createAccountDTO.setStatus("User with the provided email/username already exists");
            return new ResponseEntity<>(createAccountDTO, HttpStatus.OK);
        }
        User createdUser = registrationServices.createUser(user);
        if(createdUser != null ){
                createAccountDTO.setStatus("created");
        return new ResponseEntity<>(createAccountDTO , HttpStatus.OK);
        }
        createAccountDTO.setStatus("server error");
        return new ResponseEntity<>(createAccountDTO , HttpStatus.OK);
    }

    @PostMapping(LOGIN_MAPPING)
        public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginDto ) {
        String status = loginService.loginUser(loginDto);
        loginResponseDTO loginResponseDTO = new loginResponseDTO();
        loginResponseDTO.setStatus(status);
        if (status.equals(EMPLOYER) || status.equals(APPLICANT)) {
            return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);  // sessionToken will replace jwt in near future
        } else {
            return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
        }
    }
}
