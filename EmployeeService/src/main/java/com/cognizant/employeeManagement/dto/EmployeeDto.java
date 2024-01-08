package com.cognizant.employeeManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private String name;
    private String email;
    private String gender;
    private int age;
    private double salary;
}
