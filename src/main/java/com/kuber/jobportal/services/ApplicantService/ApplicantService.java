package com.kuber.jobportal.services.ApplicantService;

import com.kuber.jobportal.models.Dtos.ApplicantDtos.ApplicationForJobRequest;
import com.kuber.jobportal.models.Dtos.jobDtos.JobApplicationResponseDTO;
import com.kuber.jobportal.models.Dtos.jobDtos.JobDetailsDTO;
import com.kuber.jobportal.models.Job;
import com.kuber.jobportal.models.JobApplications;

public interface ApplicantService {
    JobApplicationResponseDTO submitJobApplication(ApplicationForJobRequest applicationForJobRequest);

    JobDetailsDTO getJobByJobId(int id);
}
