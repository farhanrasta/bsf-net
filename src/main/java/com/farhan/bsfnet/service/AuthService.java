package com.farhan.bsfnet.service;

import com.farhan.bsfnet.entity.User;
import com.farhan.bsfnet.model.LoginUserRequest;
import com.farhan.bsfnet.model.TokenResponse;

public interface AuthService {

    public TokenResponse login(LoginUserRequest request);

    public void logout(User user);

}
