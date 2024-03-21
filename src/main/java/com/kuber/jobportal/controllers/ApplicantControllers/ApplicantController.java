package com.kuber.jobportal.controllers.ApplicantControllers;

import com.kuber.jobportal.models.Dtos.ApplicantDtos.ApplicationForJobRequest;
import com.kuber.jobportal.models.Dtos.jobDtos.JobApplicationResponseDTO;
import com.kuber.jobportal.models.Dtos.jobDtos.JobDetailsDTO;
import com.kuber.jobportal.models.Job;
import com.kuber.jobportal.models.JobApplications;
import com.kuber.jobportal.services.ApplicantService.ApplicantService;
import com.kuber.jobportal.services.ApplicantService.ApplicantServiceImpl;
import com.kuber.jobportal.services.Validators.UserVAlidatorImpl;
import com.kuber.jobportal.services.Validators.UserValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.kuber.jobportal.services.Utility.Constants.*;

@RestController
@RequestMapping(APPLICANT_MAPPING)
@Tag(name= "ApplicationController")
public class ApplicantController {

    private UserValidator userValidator;
    private ApplicantService applicantService;

    public ApplicantController(UserVAlidatorImpl userValidator, ApplicantServiceImpl applicantService) {
        this.userValidator = userValidator;
        this.applicantService = applicantService;
    }

    @PostMapping("/applicant/jobs/job")
    public ResponseEntity<?> applyForJob(@RequestPart("resume") MultipartFile resume,
                                         @RequestPart("applicant") String applicant,
                                         @RequestPart("job") String jobId,
                                         @RequestPart("shortDescription") String shortDescription){
        int jobId1 = Integer.parseInt(jobId);
        ApplicationForJobRequest applicationForJobRequest = new ApplicationForJobRequest(applicant,shortDescription,jobId1);
        try {
            byte[] bytes = resume.getBytes();
            applicationForJobRequest.setResume(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        if(userValidator.isUser(applicationForJobRequest.getApplicant())){
//            if(userValidator.isjob(applicationForJobRequest.getJob())){
//                if(userValidator.isApplied(applicationForJobRequest.getApplicant(), applicationForJobRequest.getJob())){
//                return new ResponseEntity<>("Already Applied For This Job", HttpStatus.FOUND);
//                }
                JobApplicationResponseDTO appliedJobApplications = applicantService.submitJobApplication(applicationForJobRequest);
//                if(appliedJobApplications != null ){
                    return new ResponseEntity<>(appliedJobApplications, HttpStatus.OK);
//                }
//                return new ResponseEntity<>("Error posting job", HttpStatus.BAD_REQUEST);
//            }
//            return new ResponseEntity<>("Invalid Job", HttpStatus.UNAUTHORIZED);
//        }
//        return new ResponseEntity<>("User Not found", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{applicant}/jobs/{job}") // this will return the job looking which a applicant can apply for job
    public ResponseEntity<?> getOneJobByJobId(@PathVariable("job") int jobId, @PathVariable("applicant") String applicantId){
        if(userValidator.isUser(applicantId)){
            if(userValidator.isjob(jobId)){
                JobDetailsDTO jobDetailsDTO = applicantService.getJobByJobId(jobId);
                return new ResponseEntity<>(jobDetailsDTO, HttpStatus.OK);
            }
            return new ResponseEntity<>("Invalid Job", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("User Not found", HttpStatus.NOT_FOUND);
    }

}
