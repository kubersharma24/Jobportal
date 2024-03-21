package com.kuber.jobportal.services.jobApplicationService;

import com.kuber.jobportal.models.Dtos.jobDtos.JobApplicationResponseDTO;
import com.kuber.jobportal.models.Dtos.jobDtos.JobApplicationResposeFewDetailsDTO;
import com.kuber.jobportal.models.Dtos.jobDtos.JobDetailsDTO;
import com.kuber.jobportal.models.Dtos.resumeDTO.ResumeDTO;
import com.kuber.jobportal.models.Job;
import com.kuber.jobportal.models.JobApplications;
import com.kuber.jobportal.models.User;
import com.kuber.jobportal.repositories.JobApplicationRepo.JobApplicationRepository;
import com.kuber.jobportal.services.JobsServices.JobService;
import com.kuber.jobportal.services.JobsServices.JobServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobApplicationServiceImpl implements JobApplicationService{
    JobApplicationRepository jobApplicationRepository;
    JobService jobService;


    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository , JobServiceImpl jobService) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobService = jobService;
    }


    @Override
    public List<JobApplicationResposeFewDetailsDTO> getAllJobApplication(String email) {
        List<JobDetailsDTO> allJobsByID = jobService.getAllJobsByID(email);
        List<JobApplicationResposeFewDetailsDTO> list = new ArrayList<>();
        for(JobDetailsDTO jobDetailsDTO : allJobsByID) {
            Job job = new Job();
            job.setId(jobDetailsDTO.getJobId());
//            List<JobApplicationResponseDTO> allByJob = jobApplicationRepository.findAllByJob(job);
            List<JobApplications> allbyjob = jobApplicationRepository.findAllByJob(job);
            for(JobApplications jobApplications : allbyjob){
                JobApplicationResposeFewDetailsDTO jobApplicationResposeFewDetailsDTO = new JobApplicationResposeFewDetailsDTO();
                jobApplicationResposeFewDetailsDTO.setJobId(jobApplications.getJob().getId());
                jobApplicationResposeFewDetailsDTO.setApplicant(jobApplications.getApplicant().getEmail());
                jobApplicationResposeFewDetailsDTO.setResume(jobApplications.getResume());
                jobApplicationResposeFewDetailsDTO.setApplicationid(jobApplications.getId());
                list.add(jobApplicationResposeFewDetailsDTO);
            }
//            list.addAll(allByJob);
        }
        return list;
    }

    @Override
    public List<JobApplicationResposeFewDetailsDTO> getAllJobApplicationWithJobId(int jobId) {
        List<JobApplicationResposeFewDetailsDTO> list = new ArrayList<>();
        Job job = new Job();
        job.setId(jobId);
        List<JobApplications> allbyjob = jobApplicationRepository.findAllByJob(job);
        for(JobApplications jobApplications : allbyjob){
            JobApplicationResposeFewDetailsDTO jobApplicationResposeFewDetailsDTO = new JobApplicationResposeFewDetailsDTO();
            jobApplicationResposeFewDetailsDTO.setJobId(jobApplications.getJob().getId());
            jobApplicationResposeFewDetailsDTO.setApplicant(jobApplications.getApplicant().getEmail());
            jobApplicationResposeFewDetailsDTO.setResume(jobApplications.getResume());
            jobApplicationResposeFewDetailsDTO.setApplicationid(jobApplications.getId());
            jobApplicationResposeFewDetailsDTO.setShortDiscription(jobApplications.getShortDescription());
            list.add(jobApplicationResposeFewDetailsDTO);
        }
        return list;
    }

    @Override
    public JobApplicationResposeFewDetailsDTO getJobApplicationWithApplicationId(int applicationId) {
        JobApplications jobApplications ;
        Optional<JobApplications> byId = jobApplicationRepository.findById(applicationId);
        JobApplicationResposeFewDetailsDTO jobApplicationResposeFewDetailsDTO = new JobApplicationResposeFewDetailsDTO();
        if (byId.isPresent()) {
            jobApplications = byId.get();
            jobApplicationResposeFewDetailsDTO.setJobId(jobApplications.getJob().getId());
            jobApplicationResposeFewDetailsDTO.setApplicant(jobApplications.getApplicant().getEmail());
            jobApplicationResposeFewDetailsDTO.setResume(jobApplications.getResume());
            jobApplicationResposeFewDetailsDTO.setApplicationid(jobApplications.getId());
        }
        return jobApplicationResposeFewDetailsDTO;
    }

    @Override
    public ResumeDTO getResumeWithApplicationId(int applicationId) {
        JobApplications jobApplications = new JobApplications();
        jobApplications.setId(applicationId);
        Optional<JobApplications> byId = jobApplicationRepository.findById(applicationId);
        ResumeDTO resumeDTO = new ResumeDTO();
        if(byId.isPresent()){
            JobApplications jobApplications1 = byId.get();
            resumeDTO.setResume(jobApplications1.getResume());
        }
        return resumeDTO;
    }

}
