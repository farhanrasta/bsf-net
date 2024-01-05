package com.farhan.bsfnet.service;

import com.farhan.bsfnet.entity.User;
import com.farhan.bsfnet.model.JwtResponse;
import com.farhan.bsfnet.model.LoginUserRequest;
import com.farhan.bsfnet.model.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    public TokenResponse login(HttpServletRequest httpServletRequest, LoginUserRequest request);

    public void logout(JwtResponse jwtResponse);

}
