package com.farhan.bsfnet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name="BSF_USER_LOGON")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLogon implements Serializable {

    @Id
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "JTI", length = 200)
    private String jti;

    @Column(name = "USER_AGENT", length = 200)
    private String userAgent;

}