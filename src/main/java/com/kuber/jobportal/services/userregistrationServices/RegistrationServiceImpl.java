package com.kuber.jobportal.services.registrationServices;

import com.kuber.jobportal.models.Applicant;
import com.kuber.jobportal.models.User;
import com.kuber.jobportal.repositories.userregistrationRepos.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationServices{
    private RegistrationRepository registrationRepository;
@Autowired
public RegistrationServiceImpl(RegistrationRepository registrationRepository) {
    this.registrationRepository = registrationRepository;
}
    public User createUser(User user) {
        return registrationRepository.save(user);
    }
    @Override
    public String loginUser(String email, String password) {
        Optional<User> optionalUser = registrationRepository.findById(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (password.matches(user.getPassword())) {
                return user.getRole();
            }
            return "Password incorrect";
        }
        return "User Not Found";
    }
}
