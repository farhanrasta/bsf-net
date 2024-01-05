package com.farhan.bsfnet.service.Impl;


import com.farhan.bsfnet.entity.Employee;
import com.farhan.bsfnet.entity.User;
import com.farhan.bsfnet.model.*;
import com.farhan.bsfnet.repository.EmployeeRepository;
import com.farhan.bsfnet.service.EmployeeService;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ValidationServiceImpl validationService;

    @Override
    public EmployeeResponse create(CreateEmployeeRequest request, JwtResponse jwtResponse) {
        validationService.validate(request);

        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email already registered");
        }

        if (!request.getEmail().contains("@bankbsf.co.id")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email is not valid");
        }

        if (!request.getStatus().equals("good") && !request.getStatus().equals("bad") && !request.getStatus().equals("average")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "status is not valid");
        }

        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setSalary(request.getSalary());
        employee.setStatus(request.getStatus());
        employee.setUsername(jwtResponse.getUsername());

        employeeRepository.save(employee);

        return toEmployeeResponse(employee);

    }

    private EmployeeResponse toEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId().toString())
                .name(employee.getName())
                .email(employee.getEmail())
                .salary(employee.getSalary())
                .status(employee.getStatus())
                .username(employee.getUsername())
                .build();
    }

    @Override
    public EmployeeResponse get(JwtResponse jwtResponse, String id) {
        Employee employee = employeeRepository.findFirstByUsernameAndId(jwtResponse.getUsername(), id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee is not found"));

        return toEmployeeResponse(employee);

    }

    @Override
    public EmployeeResponse update(JwtResponse jwtResponse, UpdateEmployeeRequest request) {
        validationService.validate(request);

        Employee employee = employeeRepository.findFirstByUsernameAndId(jwtResponse.getUsername(), request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not Found"));

        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email already registered");
        }

        if (!request.getEmail().contains("@bankbsf.co.id")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email is not valid");
        }

        if (!request.getStatus().equals("good") && !request.getStatus().equals("bad") && !request.getStatus().equals("average")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "status is not valid");
        }

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setSalary(request.getSalary());
        employee.setStatus(request.getStatus());

        employeeRepository.save(employee);

        return toEmployeeResponse(employee);
    }

    @Transactional(readOnly = true)
    public Page<EmployeeResponse> search(JwtResponse jwtResponse, User user, SearchEmployeeRequest request) {
        Specification<Employee> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("user"), user));
            if (Objects.nonNull(request.getName())) {
                predicates.add(builder.like(root.get("name"), "%" + request.getName() + "%"));
            }
            if (Objects.nonNull(request.getEmail())) {
                predicates.add(builder.like(root.get("email"), "%" + request.getEmail() + "%"));
            }
            if (Objects.nonNull(request.getSalary())) {
                predicates.add(builder.like(root.get("phone"), "%" + request.getSalary() + "%"));
            }
            if (Objects.nonNull(request.getStatus())) {
                predicates.add(builder.like(root.get("status"), "%" + request.getStatus() + "%"));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Employee> contacts = employeeRepository.findAll(specification, pageable);
        List<EmployeeResponse> contactResponses = contacts.getContent().stream()
                .map(this::toEmployeeResponse)
                .toList();

        return new PageImpl<>(contactResponses, pageable, contacts.getTotalElements());
    }

    public void delete(JwtResponse jwtResponse, String employeeId) {
        Employee employee = employeeRepository.findFirstByUsernameAndId(jwtResponse.getUsername(), employeeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not Found"));

        employeeRepository.delete(employee);
    }

}
