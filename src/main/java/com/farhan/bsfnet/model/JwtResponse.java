package com.farhan.bsfnet.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class JwtResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 350066982730729014L;

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private Boolean isUserToken;

    private String token;
    private String expiredDate;


}

