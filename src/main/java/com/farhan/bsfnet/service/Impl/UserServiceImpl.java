package com.farhan.bsfnet.service.Impl;


import com.farhan.bsfnet.entity.User;
import com.farhan.bsfnet.model.JwtResponse;
import com.farhan.bsfnet.model.RegisterUserRequest;
import com.farhan.bsfnet.model.UpdateUserRequest;
import com.farhan.bsfnet.model.UserResponse;
import com.farhan.bsfnet.repository.UserRepository;
import com.farhan.bsfnet.security.BCrypt;
import com.farhan.bsfnet.service.UserService;
import com.farhan.bsfnet.service.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void register(RegisterUserRequest request){
        validationService.validate(request);

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username already registered");
        }

        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setIsUserToken(true);

        userRepository.save(user);

    }

    @Override
    public UserResponse profile(JwtResponse jwtResponse) {

        User user = userRepository.findFirstById(jwtResponse.getId().toString())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee is not found"));

        return UserResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .isUserToken(user.getIsUserToken())
                .token(jwtResponse.getToken())
                .expiredDate(jwtResponse.getExpiredDate())
                .build();
    }

    @Transactional
    public UserResponse update(UpdateUserRequest request, JwtResponse jwtResponse){
        validationService.validate(request);

        log.info("REQUEST : {}", request);

        User user = userRepository.findFirstById(jwtResponse.getId().toString())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee is not found"));

        if (Objects.nonNull(request.getFirstname())) {
            user.setFirstname(request.getFirstname());
        }

        if (Objects.nonNull(request.getLastname())) {
            user.setLastname(request.getLastname());
        }

        if (Objects.nonNull(request.getUsername())) {
            user.setUsername(request.getUsername());
        }

        if (Objects.nonNull(request.getPassword())) {
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }

        userRepository.save(user);

        log.info("USER : {}", user.getUsername());

        return UserResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .isUserToken(user.getIsUserToken())
                .build();

    }

}
