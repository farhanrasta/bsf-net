package com.farhan.bsfnet.service;

import com.farhan.bsfnet.entity.User;
import com.farhan.bsfnet.model.RegisterUserRequest;
import com.farhan.bsfnet.model.UpdateUserRequest;
import com.farhan.bsfnet.model.UserResponse;

public interface UserService {

    public void register(RegisterUserRequest request);

    public UserResponse get(User user);

    public UserResponse update(User user, UpdateUserRequest request);


}
