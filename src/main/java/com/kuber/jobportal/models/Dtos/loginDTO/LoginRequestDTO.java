package com.kuber.jobportal.models.Dtos.loginDTO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class LoginRequestDTO {
    private String email;
    private String password;

}
