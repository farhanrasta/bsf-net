package com.farhan.bsfnet.controller;


import com.farhan.bsfnet.entity.User;
import com.farhan.bsfnet.model.*;
import com.farhan.bsfnet.service.EmployeeService;
import com.farhan.bsfnet.service.Impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(
            path = "/employees",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    public WebResponse<EmployeeResponse> create(User user, @RequestBody CreateEmployeeRequest request) {
        EmployeeResponse employeeResponse = employeeService.create(user, request);
        return WebResponse.<EmployeeResponse>builder().data(employeeResponse).build();
    }

    @GetMapping(
            path = "/employees/{employeeId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<EmployeeResponse> get(User user, @PathVariable("employeeId") String employeeId) {
        EmployeeResponse employeeResponse = employeeService.get(user, employeeId);
        return WebResponse.<EmployeeResponse>builder().data(employeeResponse).build();
    }

    @PutMapping(
            path = "/employees/{employeeId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<EmployeeResponse> update(User user,
                                                @RequestBody UpdateEmployeeRequest request,
                                                @PathVariable("employeeId") String employeeId) {
        request.setId(employeeId);

        EmployeeResponse employeeResponse = employeeService.update(user, request);
        return WebResponse.<EmployeeResponse>builder().data(employeeResponse).build();
    }

    @DeleteMapping(
            path = "/employees/{employeeId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user, @PathVariable("employeeId") String employeeId) {
        employeeService.delete(user, employeeId);
        return WebResponse.<String>builder().data("Ok").build();
    }

    @GetMapping(
            path = "/employees",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<EmployeeResponse>> search(User user,
                                                      @RequestParam(value = "name", required = false) String name,
                                                      @RequestParam(value = "email", required = false) String email,
                                                      @RequestParam(value = "salary", required = false) String salary,
                                                      @RequestParam(value = "status", required = false) String status,
                                                      @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                      @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        SearchEmployeeRequest request = SearchEmployeeRequest.builder()
                .page(page)
                .size(size)
                .name(name)
                .email(email)
                .salary(salary)
                .status(status)
                .build();

        Page<EmployeeResponse> contactResponses = employeeService.search(user, request);
        return WebResponse.<List<EmployeeResponse>>builder()
                .data(contactResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(contactResponses.getNumber())
                        .totalPage(contactResponses.getTotalPages())
                        .size(contactResponses.getSize())
                        .build())
                .build();
    }

}
