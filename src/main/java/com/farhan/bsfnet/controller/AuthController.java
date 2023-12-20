package com.farhan.bsfnet.controller;
//cobalah

import com.farhan.bsfnet.entity.User;
import com.farhan.bsfnet.model.LoginUserRequest;
import com.farhan.bsfnet.model.TokenResponse;
import com.farhan.bsfnet.model.WebResponse;
import com.farhan.bsfnet.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
//sad
    @Autowired
    private AuthService authService;

    @PostMapping (
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private WebResponse<TokenResponse> login (@RequestBody LoginUserRequest request) {
        TokenResponse tokenResponse = authService.login(request);
        return WebResponse.<TokenResponse>builder().data(tokenResponse).build();

    }

    @DeleteMapping(path = "/logout",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> logout(User user) {
        authService.logout(user);
        return WebResponse.<String>builder().data("Ok").build();
    }
}
