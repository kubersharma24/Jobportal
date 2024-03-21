package com.kuber.jobportal.services.JobsServices;

import com.kuber.jobportal.models.Dtos.jobDtos.JobCreationRequestDTO;
import com.kuber.jobportal.models.Dtos.jobDtos.JobDetailsDTO;
import com.kuber.jobportal.models.Job;
import com.kuber.jobportal.models.User;
import com.kuber.jobportal.repositories.JobRepo.JobRepository;
import com.kuber.jobportal.services.userregistrationServices.RegistrationServiceImpl;
import com.kuber.jobportal.services.userregistrationServices.RegistrationServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    public JobRepository jobRepository;
    private RegistrationServices userService;

    public JobServiceImpl(JobRepository jobRepository , RegistrationServiceImpl registrationService) {
        this.jobRepository = jobRepository;
        this.userService = registrationService;
    }


    @Override
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public void createJob(User employer, JobCreationRequestDTO jobRequestDto) {
        Job job = new Job();
        job.setTitle(jobRequestDto.getTitle());
        job.setCompany(jobRequestDto.getCompany());
        job.setLocation(jobRequestDto.getLocation());
        job.setDescription(jobRequestDto.getDescription());
        job.setEmployer(employer);
        jobRepository.save(job);
    }

//    @Override
//    public List<Job> getAllJobsByID(String email) {
//        User user = new User();
//        user.setEmail(email);
//        return jobRepository.findAllByEmployer(user);
//    }
    public List<JobDetailsDTO> getAllJobsByID(String email ) {
    User user = new User();
    user.setEmail(email);
    List<Job> jobs = jobRepository.findAllByEmployer(user);
    return mapJobsToDTOs(jobs);
    }

    private List<JobDetailsDTO> mapJobsToDTOs(List<Job> jobs) {
        List<JobDetailsDTO> dtos = new ArrayList<>();
        for (Job job : jobs) {
            JobDetailsDTO dto = new JobDetailsDTO();
            dto.setJobId(job.getId());
            dto.setTitle(job.getTitle());
            dto.setCompany(job.getCompany());
            dto.setLocation(job.getLocation());
            dto.setDescription(job.getDescription());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public Job getOneJobById(int id) {
        Job job = null;
        Optional<Job> byId = jobRepository.findById(id);
        if (byId.isPresent()) {
            job = byId.get();
        }
        return job;
    }

    @Override
    public int DeleteAllJobsWithEmployerId(String email) {
        User user = new User();
        user.setEmail(email);
        List<Job> allByEmployer = jobRepository.findAllByEmployer(user);

        for (Job job : allByEmployer) {
            jobRepository.delete(job);
        }

        return allByEmployer.size();
    }

    @Override
    public int DeleteSingleJobWithJobId(int id) {
        Optional<Job> byId = jobRepository.findById(id);
        if(byId.isPresent()){
            Job job = byId.get();
            jobRepository.delete(job);
            return 1;
        }
        return 0;
    }

    @Override
    public JobDetailsDTO getOneJobByJobId(int id) {
        Job job = getOneJobById(id);
        JobDetailsDTO jobDetailsDTO = new JobDetailsDTO();
        jobDetailsDTO.setJobId(job.getId());
        jobDetailsDTO.setTitle(job.getTitle());
        jobDetailsDTO.setCompany(job.getCompany());
        jobDetailsDTO.setLocation(job.getLocation());
        jobDetailsDTO.setDescription(job.getDescription());
        return jobDetailsDTO;
    }

    @Override
    public List<JobDetailsDTO> getAllJobs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Job> jobPage = jobRepository.findAll(pageable);
        List<JobDetailsDTO> jobDTOList = jobPage.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return jobDTOList;
    }

    private JobDetailsDTO mapToDTO(Job job) {
        JobDetailsDTO dto = new JobDetailsDTO();
        dto.setJobId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setCompany(job.getCompany());
        dto.setLocation(job.getLocation());
        dto.setDescription(job.getDescription());
        return dto;
    }
}
