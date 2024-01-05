package com.farhan.bsfnet.service.Impl;


import com.farhan.bsfnet.entity.User;
import com.farhan.bsfnet.entity.UserLogon;
import com.farhan.bsfnet.model.JwtResponse;
import com.farhan.bsfnet.model.LoginUserRequest;
import com.farhan.bsfnet.model.TokenResponse;
import com.farhan.bsfnet.model.UserResponse;
import com.farhan.bsfnet.repository.UserLogonRepository;
import com.farhan.bsfnet.repository.UserRepository;
import com.farhan.bsfnet.security.BCrypt;
import com.farhan.bsfnet.service.AuthService;
import com.farhan.bsfnet.service.JwtService;
import com.farhan.bsfnet.service.LoginService;
import com.farhan.bsfnet.service.ValidationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLogonRepository userLogonRepository;

    @Autowired
    private ValidationService validationService;

    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public TokenResponse login(HttpServletRequest httpServletRequest, LoginUserRequest request) {
        validationService.validate(request);

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid username of password"));

        if(BCrypt.checkpw(request.getPassword(), user.getPassword())) {

            user.setUsername(request.getUsername());
            userRepository.save(user);

            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setFirstname(user.getFirstname());
            userResponse.setLastname(user.getLastname());
            userResponse.setUsername(user.getUsername());
            userResponse.setIsUserToken(user.getIsUserToken());

            JwtResponse jwtResponse = jwtService.generate(httpServletRequest, userResponse);
            userResponse.setToken(jwtResponse.getToken());
            userResponse.setExpiredDate(jwtResponse.getExpiredDate());

            return TokenResponse.builder()
                    .username(userResponse.getUsername())
                    .token(userResponse.getToken())
                    .expiredAt(userResponse.getExpiredDate())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid username of password");
        }
    }

    @Override
    public void logout(JwtResponse jwtResponse) {
        loginService.loginSave(jwtResponse.getId(), jwtResponse.getId()+"LOGOUT"+sdf1.format(new Date()), null);
    }

}
