package com.kuber.jobportal.services.loginServices;

import com.kuber.jobportal.models.Dtos.loginDTO.LoginRequestDTO;

public interface LoginService {
    String loginUser(LoginRequestDTO loginDto);
}
