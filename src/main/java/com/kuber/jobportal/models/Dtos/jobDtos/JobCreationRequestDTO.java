package com.kuber.jobportal.models.Dtos.jobDtos;

import com.kuber.jobportal.models.User;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobRequestDTO {
    private String employer;
    private String title;
    private String company;
    private String location ;
    private String description;
}
