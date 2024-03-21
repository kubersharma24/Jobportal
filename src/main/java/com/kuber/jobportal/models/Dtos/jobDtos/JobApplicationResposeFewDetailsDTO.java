package com.kuber.jobportal.models.Dtos.jobDtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JobApplicationResposeFewDetailsDTO {
    String shortDiscription;
    int applicationid;
    int jobId;
    String applicant;
    byte [] resume;
}
