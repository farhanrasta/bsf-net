package com.farhan.bsfnet.service.Impl;

import com.farhan.bsfnet.entity.UserLogon;
import com.farhan.bsfnet.repository.UserLogonRepository;
import com.farhan.bsfnet.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserLogonRepository userLogonRepository;

    @Override
    public void loginSave(Long userId, String jti, String userAgent) {

        UserLogon logon = userLogonRepository.findByUserId(userId);
        if(logon==null) {
            logon = new UserLogon();
            logon.setUserId(userId);
        }

        logon.setJti(jti);
        logon.setUserAgent(userAgent);
        userLogonRepository.save(logon);
    }

}
