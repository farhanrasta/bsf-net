package com.farhan.bsfnet.controller;


import com.farhan.bsfnet.entity.User;
import com.farhan.bsfnet.model.*;
import com.farhan.bsfnet.service.Impl.UserServiceImpl;
import com.farhan.bsfnet.service.JwtService;
import com.farhan.bsfnet.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping(path = "/users/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register(@RequestBody RegisterUserRequest request) {
        userService.register(request);
        return WebResponse.<String>builder().data("Ok").build();
    }

    @GetMapping(
            path = "/users/current",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> profile(HttpServletRequest httpServletRequest) {
        JwtResponse jwtResponse = jwtService.filter(httpServletRequest);
        UserResponse userResponse = userService.profile(jwtResponse);
        return WebResponse.<UserResponse>builder().data(userResponse).build();

    }

    @PatchMapping(
            path = "/users/current",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> update(@RequestBody UpdateUserRequest request, HttpServletRequest httpServletRequest) {
        JwtResponse jwtResponse = jwtService.filter(httpServletRequest);
        UserResponse userResponse = userService.update(request, jwtResponse);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }

}
