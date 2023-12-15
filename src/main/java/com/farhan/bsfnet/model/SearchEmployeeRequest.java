package com.farhan.bsfnet.model;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchEmployeeRequest {

    private String name;

    private String email;

    private String salary;

    private String status;

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;

}
