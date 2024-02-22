package com.kuber.jobportal.services.Validators;

import com.kuber.jobportal.models.Job;
import com.kuber.jobportal.models.User;
import com.kuber.jobportal.repositories.JobApplicationRepo.JobApplicationRepository;
import com.kuber.jobportal.repositories.JobRepo.JobRepository;
import com.kuber.jobportal.repositories.userregistrationRepo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.kuber.jobportal.services.Utility.Constants.EMPLOYER;

@Service
public class UserVAlidatorImpl implements UserValidator{
    private UserRepository registrationRepository;
    private JobRepository jobRepository;
    private JobApplicationRepository jobApplicationRepository;

    public UserVAlidatorImpl(UserRepository registrationRepository, JobRepository jobRepository, JobApplicationRepository jobApplicationRepository) {
        this.registrationRepository = registrationRepository;
        this.jobRepository = jobRepository;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    @Override
    public boolean isUser(String id) {
        if(registrationRepository.existsById(id))
            return true;
        return false;
    }

    @Override
    public boolean isEmployer(String email) {
        Optional<User> optionalUser = registrationRepository.findById(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(user.getRole().equals(EMPLOYER)) return true;
        }
        return false;
    }

    @Override
    public boolean isjob(int job) {
        return jobRepository.existsById(job);
    }

    @Override
    public boolean isApplication(int applicationId) {
        return jobApplicationRepository.existsById(applicationId);
    }

    @Override
    public boolean isApplied(String applicant, int jobId) {
        User user = new User();
        user.setEmail(applicant);

        Job job = new Job();
        job.setId(jobId);

        return jobApplicationRepository.existsByJobAndApplicant(job, user);
    }
}
