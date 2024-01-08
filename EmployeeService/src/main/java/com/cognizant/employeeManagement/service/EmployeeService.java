package com.cognizant.employeeManagement.service;

import com.cognizant.employeeManagement.dto.EmployeeDto;
import com.cognizant.employeeManagement.model.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    List<Employee> getEmployees();
    ResponseEntity<Map> addEmployee(String requestTokenHeader, EmployeeDto employeeDto);
    ResponseEntity<?> deleteEmployee(String requestTokenHeader,long id);
    Employee updateEmployee(long id, Employee employee);
    Employee getEmployeeById(long id);
}
