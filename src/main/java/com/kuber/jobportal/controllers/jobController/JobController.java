package com.kuber.jobportal.controllers.jobController;

import com.kuber.jobportal.models.Dtos.jobDtos.JobCreationRequestDTO;
import com.kuber.jobportal.models.Dtos.jobDtos.JobDetailsDTO;
import com.kuber.jobportal.models.Dtos.jobDtos.UserIdDTO;
import com.kuber.jobportal.models.Dtos.jobDtos.UserId_JobIdDTO;
import com.kuber.jobportal.models.Job;
import com.kuber.jobportal.models.User;
import com.kuber.jobportal.services.JobsServices.JobServiceImpl;
import com.kuber.jobportal.services.JobsServices.JobService;
import com.kuber.jobportal.services.Validators.UserVAlidatorImpl;
import com.kuber.jobportal.services.Validators.UserValidator;
import com.kuber.jobportal.services.userregistrationServices.RegistrationServiceImpl;
import com.kuber.jobportal.services.userregistrationServices.RegistrationServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kuber.jobportal.services.Utility.Constants.*;

@RestController
@RequestMapping(USERS)
public class JobController {
    public JobService jobsService;
    private RegistrationServices userService;
    private UserValidator userValidator;

    public JobController(JobServiceImpl jobsService , RegistrationServiceImpl registrationServices , UserVAlidatorImpl userValidator) {
        this.jobsService = jobsService;
        this.userService = registrationServices;
        this.userValidator = userValidator;
    }
    @PostMapping(USER_JOBS)
    public ResponseEntity<?> createJob(@RequestBody JobCreationRequestDTO jobRequestDto){
        User employer = userService.getUserByEmail(jobRequestDto.getEmployer());
        if(employer!=null){
            if(userValidator.isEmployer(employer.getEmail())){
                try{
                   Job job = jobsService.createJob(employer,jobRequestDto);
                    return new ResponseEntity<>(job, HttpStatus.CREATED);
                }catch (Exception e){
                    return new ResponseEntity<>("error creating the job", HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>("Employer Not Found", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Bad Request", HttpStatus.UNAUTHORIZED);
    }


    @GetMapping(USER_JOBS_PATH_VARIABLE)
    public ResponseEntity<?> getAllJobsByEmployerId(@PathVariable String user){
        UserIdDTO userIdDTO = new UserIdDTO();
        userIdDTO.setEmail(user);
        if(userValidator.isUser(userIdDTO.getEmail())){
            if(userValidator.isEmployer(userIdDTO.getEmail())){
                List<JobDetailsDTO> job = jobsService.getAllJobsByID(userIdDTO.getEmail());
                if(job.size()==0){
                    return new ResponseEntity<>("No Jobs Available", HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(job, HttpStatus.OK);
            }
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("User Not found", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(USER_JOBS_JOB)
    public ResponseEntity<?> getOneJobByJobId(@PathVariable("user") String user, @PathVariable("job") int job){
        var userIdJobIdDTO = new UserId_JobIdDTO();
        userIdJobIdDTO.setId(job);
        userIdJobIdDTO.setEmail(user);
        if(userValidator.isUser(userIdJobIdDTO.getEmail())){
            if(userValidator.isEmployer(userIdJobIdDTO.getEmail())){
                if(userValidator.isjob(job)){
                    JobDetailsDTO  jobDetailsDTO = jobsService.getOneJobByJobId(job);
                    return new ResponseEntity<>(jobDetailsDTO, HttpStatus.UNAUTHORIZED);
                }
                return new ResponseEntity<>("No Jobs Available", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("User Not found", HttpStatus.UNAUTHORIZED);
    }
    @DeleteMapping(USER_JOBS_PATH_VARIABLE)
    public ResponseEntity<?> deleteAllJobByEmployerId(@PathVariable("user") String email){
        if(userValidator.isUser(email)){
            if(userValidator.isEmployer(email)){
                int effected_Jobs = jobsService.DeleteAllJobsWithEmployerId(email);
                if(effected_Jobs == 0){
                    return new ResponseEntity<>("No Jobs To Delete", HttpStatus.ACCEPTED);
                }else{
                    return new ResponseEntity<>("Deleted jobs : "+ effected_Jobs, HttpStatus.ACCEPTED);
                }
            }
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("User Not found", HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping(USER_JOBS_JOB)
    public ResponseEntity<?> deleteSingleJobByJobId(@PathVariable("user") String email, @PathVariable("job") int id){
        if(userValidator.isUser(email)){
            if(userValidator.isEmployer(email)){
                int effected_Jobs = jobsService.DeleteSingleJobWithJobId(id);
                if(effected_Jobs == 0){
                    return new ResponseEntity<>("No Jobs To Delete", HttpStatus.ACCEPTED);
                }else{
                    return new ResponseEntity<>("Deleted jobs : "+ effected_Jobs, HttpStatus.ACCEPTED);
                }
            }
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("User Not found", HttpStatus.UNAUTHORIZED);
    }
}
