package com.kuber.jobportal.services.registrationServices;

import com.kuber.jobportal.models.User;

public interface RegistrationServices {
    User createUser(User user);

    String loginUser(String email, String password);
}
