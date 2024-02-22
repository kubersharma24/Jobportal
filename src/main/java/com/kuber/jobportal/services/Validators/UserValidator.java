package com.kuber.jobportal.services.Validators;

import com.kuber.jobportal.models.User;

public interface UserValidator {
    boolean isUser(String id);

    boolean isEmployer(String email);

    boolean isjob(int job);

    boolean isApplication(int applicationId);

    boolean isApplied(String applicant, int job);
}
