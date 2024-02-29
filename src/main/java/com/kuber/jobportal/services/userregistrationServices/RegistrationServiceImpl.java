package com.kuber.jobportal.services.userregistrationServices;

import com.kuber.jobportal.models.User;
import com.kuber.jobportal.repositories.userregistrationRepo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationServices{
    private UserRepository registrationRepository;
@Autowired
public RegistrationServiceImpl(UserRepository registrationRepository) {
    this.registrationRepository = registrationRepository;
}
    public User createUser(User user) {
        return registrationRepository.save(user);
    }

    @Override
    public User getUserByEmail(String employer) {
        User user = null;
        Optional<User> byId = registrationRepository.findById(employer);
        if (byId.isPresent()) {
            user = byId.get();
        }
        return user;
    }

}
