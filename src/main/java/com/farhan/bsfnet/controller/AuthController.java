package com.farhan.bsfnet.controller;
//cobalah

import com.farhan.bsfnet.entity.User;
import com.farhan.bsfnet.model.JwtResponse;
import com.farhan.bsfnet.model.LoginUserRequest;
import com.farhan.bsfnet.model.TokenResponse;
import com.farhan.bsfnet.model.WebResponse;
import com.farhan.bsfnet.service.AuthService;
import com.farhan.bsfnet.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
//sad
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping (
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TokenResponse> login (HttpServletRequest httpServletRequest, @RequestBody LoginUserRequest request) {
        TokenResponse tokenResponse = authService.login(httpServletRequest, request);
        return WebResponse.<TokenResponse>builder().data(tokenResponse).build();

    }

    @GetMapping(path = "/logout",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> login(HttpServletRequest httpServletRequest) {
        JwtResponse jwtResponse = jwtService.filter(httpServletRequest);
        authService.logout(jwtResponse);
        return WebResponse.<String>builder().data("Ok").build();
    }
}
