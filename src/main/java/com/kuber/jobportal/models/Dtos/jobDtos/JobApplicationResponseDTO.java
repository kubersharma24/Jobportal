package com.kuber.jobportal.models.Dtos.jobDtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobApplicationResponseDTO {
    private int jobId;
    private String employer;
    private String title;
    private String company;
    private String location ;
    private String description;
    private byte [] resume;
}
