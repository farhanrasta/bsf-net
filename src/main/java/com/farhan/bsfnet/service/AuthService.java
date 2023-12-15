package com.farhan.bsfnet.service;


import com.farhan.bsfnet.entity.User;
import com.farhan.bsfnet.model.LoginUserRequest;
import com.farhan.bsfnet.model.TokenResponse;
import com.farhan.bsfnet.repository.UserRepository;
import com.farhan.bsfnet.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public TokenResponse login(LoginUserRequest request) {
        validationService.validate(request);

        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid username of password"));

        if(BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(oneHour());
            userRepository.save(user);

            return TokenResponse.builder().
                    token(user.getToken()).
                    expiredAt(user.getTokenExpiredAt())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid username of password");
        }
    }

    private Long oneHour() {

        return System.currentTimeMillis() + (1000 * 60 * 1);
    }

    public void logout(User user) {
        user.setToken(null);
        user.setTokenExpiredAt(null);

        userRepository.save(user);
    }

}
