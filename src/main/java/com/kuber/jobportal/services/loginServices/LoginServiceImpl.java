package com.kuber.jobportal.services.loginServices;

import com.kuber.jobportal.models.Dtos.loginDTO.LoginRequestDTO;
import com.kuber.jobportal.models.User;
import com.kuber.jobportal.repositories.userregistrationRepo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LoginServiceImpl implements LoginService{
    private UserRepository registrationRepository;

    public LoginServiceImpl(UserRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public String loginUser(LoginRequestDTO loginDto) {
        Optional<User> optionalUser = registrationRepository.findById(loginDto.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (loginDto.getPassword().matches(user.getPassword())) {
                return user.getRole();
            }
            return "Password incorrect";
        }
            return "User Not Found";
    }
}
