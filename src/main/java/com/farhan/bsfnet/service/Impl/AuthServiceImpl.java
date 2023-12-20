package com.farhan.bsfnet.service.Impl;


import com.farhan.bsfnet.entity.User;
import com.farhan.bsfnet.model.LoginUserRequest;
import com.farhan.bsfnet.model.TokenResponse;
import com.farhan.bsfnet.repository.UserRepository;
import com.farhan.bsfnet.security.BCrypt;
import com.farhan.bsfnet.service.AuthService;
import com.farhan.bsfnet.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public TokenResponse login(LoginUserRequest request) {
        validationService.validate(request);

        LocalDateTime dateTime2 = LocalDateTime.now().plusHours(1);

        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid username of password"));

        if(BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(dateTime2);
            userRepository.save(user);

            return TokenResponse.builder().
                    token(user.getToken()).
                    expiredAt(user.getTokenExpiredAt())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid username of password");
        }
    }

    public void logout(User user) {
        user.setToken(null);
        user.setTokenExpiredAt(null);

        userRepository.save(user);
    }

}
