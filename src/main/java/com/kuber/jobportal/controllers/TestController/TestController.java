package com.kuber.jobportal.controllers.TestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/test")
public class TestController {
    @PostMapping("/multipart")
    public ResponseEntity<?> handelMultiPartFile(@RequestPart("resume") MultipartFile resume,
                                                 @RequestPart("applicant") String applicant,
                                                 @RequestPart("job") int jobId ){
        MultipartFile resume1 = resume;
        try {
            byte [] b = resume.getBytes();
            System.out.println(jobId+"  "+b+"    "+ applicant);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>("reached", HttpStatus.OK);
    }
}
