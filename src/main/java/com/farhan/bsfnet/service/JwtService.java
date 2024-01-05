package com.farhan.bsfnet.service;

import com.farhan.bsfnet.model.JwtResponse;
import com.farhan.bsfnet.model.UserResponse;
import com.farhan.bsfnet.service.Impl.JwtServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;

public interface JwtService {

    public JwtResponse generate(HttpServletRequest httpServletRequest, UserResponse userResponse);

    public JwtResponse filter(HttpServletRequest httpServletRequest);

}
