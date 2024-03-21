package com.kuber.jobportal.services.jobApplicationService;

import com.kuber.jobportal.models.Dtos.jobDtos.JobApplicationResponseDTO;
import com.kuber.jobportal.models.Dtos.jobDtos.JobApplicationResposeFewDetailsDTO;
import com.kuber.jobportal.models.Dtos.resumeDTO.ResumeDTO;

import java.util.List;

public interface JobApplicationService {
    List<JobApplicationResposeFewDetailsDTO> getAllJobApplication(String email);

    List<JobApplicationResposeFewDetailsDTO> getAllJobApplicationWithJobId(int jobId);

    JobApplicationResposeFewDetailsDTO getJobApplicationWithApplicationId(int applicationId);

    ResumeDTO getResumeWithApplicationId(int applicationId);
}
