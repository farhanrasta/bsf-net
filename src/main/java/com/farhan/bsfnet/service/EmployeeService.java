package com.farhan.bsfnet.service;

import com.farhan.bsfnet.entity.Employee;
import com.farhan.bsfnet.entity.User;
import com.farhan.bsfnet.model.*;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    public EmployeeResponse create(CreateEmployeeRequest request, JwtResponse jwtResponse);

    public EmployeeResponse get(JwtResponse jwtResponse, String id);

    public EmployeeResponse update(JwtResponse jwtResponse, UpdateEmployeeRequest request);

    public Page<EmployeeResponse> search(JwtResponse jwtResponse, User user, SearchEmployeeRequest request);

    public void delete(JwtResponse jwtResponse, String employeeId);

}
