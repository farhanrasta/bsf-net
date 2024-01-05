package com.farhan.bsfnet.repository;

import com.farhan.bsfnet.entity.UserLogon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogonRepository extends JpaRepository<UserLogon, Long> {

    public UserLogon findByUserId(Long userId);
    public Boolean existsByUserIdAndJti(Long userId, String jti);

}
