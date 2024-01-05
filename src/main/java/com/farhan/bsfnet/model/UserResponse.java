package com.farhan.bsfnet.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long id;

    private String firstname;

    private String lastname;

    private String username;

    private Boolean isUserToken;

    private String token;

    private String expiredDate;

}
