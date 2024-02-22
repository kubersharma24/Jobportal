package com.kuber.jobportal.models.Dtos.ApplicantDtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor
public class ApplicationForJobRequest {
    private String applicant;
    private int job;
    private String shortDescription;
    private byte [] resume;
//    private MultipartFile resume;

    public ApplicationForJobRequest(String applicant, String shortDescription , int job) {
        this.applicant = applicant;
        this.shortDescription = shortDescription;
        this.job = job;
    }

}
