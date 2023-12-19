package com.farhan.bsfnet.service;

import com.farhan.bsfnet.entity.Employee;
import com.farhan.bsfnet.entity.User;
import com.farhan.bsfnet.model.CreateEmployeeRequest;
import com.farhan.bsfnet.model.EmployeeResponse;
import com.farhan.bsfnet.model.SearchEmployeeRequest;
import com.farhan.bsfnet.model.UpdateEmployeeRequest;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    public EmployeeResponse create(User user, CreateEmployeeRequest request);

    public EmployeeResponse get(User user, String id);

    public EmployeeResponse update(User user, UpdateEmployeeRequest request);

    public Page<EmployeeResponse> search(User user, SearchEmployeeRequest request);

    public void delete(User user, String employeeId);

}
