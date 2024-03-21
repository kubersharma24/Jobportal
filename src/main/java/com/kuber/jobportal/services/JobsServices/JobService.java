package com.kuber.jobportal.services.JobsServices;

import com.kuber.jobportal.models.Dtos.jobDtos.JobCreationRequestDTO;
import com.kuber.jobportal.models.Dtos.jobDtos.JobDetailsDTO;
import com.kuber.jobportal.models.Job;
import com.kuber.jobportal.models.User;
import org.hibernate.query.Page;

import java.util.List;

public interface JobService {
    Job createJob(Job job);

    void createJob(User employer, JobCreationRequestDTO jobRequestDto);

//    List<Job> getAllJobsByID(String email);
    List<JobDetailsDTO> getAllJobsByID(String email );
    Job getOneJobById(int id);

    int DeleteAllJobsWithEmployerId(String email);

    int DeleteSingleJobWithJobId(int id);

    JobDetailsDTO getOneJobByJobId(int job);

    List<JobDetailsDTO> getAllJobs(int page, int size);

}
