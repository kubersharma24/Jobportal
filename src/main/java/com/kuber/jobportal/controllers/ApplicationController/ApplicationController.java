package com.kuber.jobportal.controllers.ApplicationController;

import com.kuber.jobportal.models.Dtos.jobDtos.JobApplicationResponseDTO;
import com.kuber.jobportal.models.Dtos.jobDtos.JobApplicationResposeFewDetailsDTO;
import com.kuber.jobportal.models.Dtos.jobDtos.JobDetailsDTO;
import com.kuber.jobportal.models.Dtos.resumeDTO.ResumeDTO;
import com.kuber.jobportal.services.JobsServices.JobService;
import com.kuber.jobportal.services.JobsServices.JobServiceImpl;
import com.kuber.jobportal.services.Validators.UserVAlidatorImpl;
import com.kuber.jobportal.services.Validators.UserValidator;
import com.kuber.jobportal.services.jobApplicationService.JobApplicationService;
import com.kuber.jobportal.services.jobApplicationService.JobApplicationServiceImpl;
import com.kuber.jobportal.services.userregistrationServices.RegistrationServiceImpl;
import com.kuber.jobportal.services.userregistrationServices.RegistrationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kuber.jobportal.services.Utility.Constants.*;


@RestController
@RequestMapping(USERS)
public class ApplicationController {
    private RegistrationServices userService;
    private UserValidator userValidator;
    private JobApplicationService jobApplicationService;
    private JobService jobService;
    @Autowired

    public ApplicationController(JobServiceImpl jobService, RegistrationServiceImpl userService, UserVAlidatorImpl userValidator, JobApplicationServiceImpl jobApplicationService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.jobApplicationService = jobApplicationService;
        this.jobService = jobService;
    }

    @GetMapping(USER_JOBS_JOB_APPLICATIONS_)
    public ResponseEntity<?> getAllSubimittedApplications(@PathVariable("user") String email){
        if(userValidator.isUser(email)){
            if(userValidator.isEmployer(email)){
                List<JobApplicationResposeFewDetailsDTO> list = jobApplicationService.getAllJobApplication(email);
                return new ResponseEntity<>(list, HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("User Not found", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(USER_JOBS_JOB_APPLICATIONS)
    public ResponseEntity<?> getAllSubimittedApplicationsByJobId(
            @PathVariable("user") String email, @PathVariable("job") int jobId){
                List<JobApplicationResposeFewDetailsDTO> list = jobApplicationService.getAllJobApplicationWithJobId(jobId);
                return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/{user}/jobs/{job}/applications/{application}/resume")
    public ResponseEntity<?> getResumeByApplicationsId(
            @PathVariable("user") String email, @PathVariable("job") int jobId , @PathVariable("application") int applicationId){
        ResumeDTO resumeDTO = jobApplicationService.getResumeWithApplicationId(applicationId);
        return new ResponseEntity<>(resumeDTO, HttpStatus.OK);
    }



    @GetMapping(USRE_JOBS_JOB_APPLICANTS_APPLICANT)
    public ResponseEntity<?> getJobApplicationWithApplicationId(@PathVariable("user") String email,
                                                                @PathVariable("job") int jobid,
                                                                @PathVariable("application") int applicationId){
        if(userValidator.isUser(email)){
            if(userValidator.isEmployer(email)){
                if(userValidator.isjob(jobid)){
                    if(userValidator.isApplication(applicationId)){
                        JobApplicationResposeFewDetailsDTO jobApplicationResposeFewDetailsDTO = jobApplicationService.getJobApplicationWithApplicationId(applicationId);
                        return new ResponseEntity<>(jobApplicationResposeFewDetailsDTO, HttpStatus.OK);
                    }
                    return new ResponseEntity<>("ResponseId not found", HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>("Job not Found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("User Not found", HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/applicants/jobs") // applicant home page
    public ResponseEntity<?> getAllJobsWithPageination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
            List<JobDetailsDTO> jobs = jobService.getAllJobs(page, size);
            return new ResponseEntity<>(jobs,HttpStatus.OK);
    }
}
