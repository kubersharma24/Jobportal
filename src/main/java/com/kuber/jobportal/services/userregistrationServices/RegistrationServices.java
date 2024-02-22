package com.kuber.jobportal.services.userregistrationServices;

import com.kuber.jobportal.models.User;

public interface RegistrationServices {
    User createUser(User user);

    User getUserByEmail(String employer);
}
