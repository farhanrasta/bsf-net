package com.farhan.bsfnet.repository;

import com.farhan.bsfnet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findFirstByToken(String token);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Optional<User> findFirstById(String id);
}
