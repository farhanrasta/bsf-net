package com.farhan.bsfnet.repository;

import com.farhan.bsfnet.entity.Employee;
import com.farhan.bsfnet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {

    Optional<Employee> findFirstByUserAndId(User user, String id);

}
