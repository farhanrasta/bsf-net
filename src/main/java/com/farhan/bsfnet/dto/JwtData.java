package com.farhan.bsfnet.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Builder
public class JwtData implements Serializable {

    @Serial
    private static final long serialVersionUID = -6867955810374513376L;

    private Date date;
    private Long f1; //ID
    private String f2; //FIRSTNAME
    private String f3; //LASTNAME
    private String f4; //USERNAME
    private Boolean f5; //isUserToken

}
