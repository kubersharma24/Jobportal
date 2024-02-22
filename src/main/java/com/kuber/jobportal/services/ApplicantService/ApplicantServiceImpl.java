package com.kuber.jobportal.services.ApplicantService;

import com.kuber.jobportal.models.Dtos.ApplicantDtos.ApplicationForJobRequest;
import com.kuber.jobportal.models.Dtos.jobDtos.JobApplicationResponseDTO;
import com.kuber.jobportal.models.Dtos.jobDtos.JobDetailsDTO;
import com.kuber.jobportal.models.Job;
import com.kuber.jobportal.models.JobApplications;
import com.kuber.jobportal.models.User;
import com.kuber.jobportal.repositories.JobApplicationRepo.JobApplicationRepository;
import com.kuber.jobportal.services.JobsServices.JobService;
import com.kuber.jobportal.services.JobsServices.JobServiceImpl;
import com.kuber.jobportal.services.userregistrationServices.RegistrationServiceImpl;
import com.kuber.jobportal.services.userregistrationServices.RegistrationServices;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
public class ApplicantServiceImpl implements ApplicantService{

    private JobApplicationRepository jobApplicationRepository;
    private RegistrationServices userService;
    private JobService jobService;

    public ApplicantServiceImpl(JobApplicationRepository jobApplicationRepository, RegistrationServiceImpl userService, JobServiceImpl jobService) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.userService = userService;
        this.jobService = jobService;
    }

    @Override
    public JobApplicationResponseDTO submitJobApplication(ApplicationForJobRequest applicationForJobRequest) {

        User applicant = userService.getUserByEmail(applicationForJobRequest.getApplicant());
        Job job = jobService.getOneJobById(applicationForJobRequest.getJob());
        JobApplications jobApplications = new JobApplications();
        jobApplications.setShortDescription(applicationForJobRequest.getShortDescription());
        jobApplications.setApplicant(applicant);
        jobApplications.setJob(job);
        jobApplications.setResume(applicationForJobRequest.getResume());
        JobApplicationResponseDTO jobApplicationResponseDTO = new JobApplicationResponseDTO();
        JobApplications save = jobApplicationRepository.save(jobApplications);

        jobApplicationResponseDTO.setJobId(save.getId());
        jobApplicationResponseDTO.setDescription(save.getShortDescription());
        jobApplicationResponseDTO.setResume(save.getResume());
        jobApplicationResponseDTO.setEmployer(job.getEmployer().getEmail());
        jobApplicationResponseDTO.setTitle(job.getTitle());
        jobApplicationResponseDTO.setCompany(job.getCompany());
        jobApplicationResponseDTO.setLocation(job.getLocation());

        return jobApplicationResponseDTO;
    }

    @Override
    public JobDetailsDTO getJobByJobId(int id) {
            Job job = jobService.getOneJobById(id);
            JobDetailsDTO jobDetailsDTO = new JobDetailsDTO();
            jobDetailsDTO.setJobId(job.getId());
            jobDetailsDTO.setTitle(job.getTitle());
            jobDetailsDTO.setCompany(job.getCompany());
            jobDetailsDTO.setLocation(job.getLocation());
            jobDetailsDTO.setDescription(job.getDescription());
            return jobDetailsDTO;
    }
}
