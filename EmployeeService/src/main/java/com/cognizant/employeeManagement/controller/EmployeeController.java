package com.cognizant.employeeManagement.controller;

import com.cognizant.employeeManagement.dto.EmployeeDto;
import com.cognizant.employeeManagement.exception.AuthorizationException;
import com.cognizant.employeeManagement.feign.RegistrationFeign;
import com.cognizant.employeeManagement.model.Employee;
import com.cognizant.employeeManagement.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RegistrationFeign registrationFeign;

    @GetMapping
    public List<Employee> getEmployees(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException {
        if(registrationFeign.authorizeTheRequest(requestTokenHeader)) {
            return employeeService.getEmployees();
        }
        else
            throw new AuthorizationException("Invalid JWT Token");
    }
    @GetMapping("/{id}")
    public Employee getEmployeeById(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, @PathVariable("id") long id) throws AuthorizationException {
        if(registrationFeign.authorizeTheRequest(requestTokenHeader))
            return employeeService.getEmployeeById(id);
        else
            throw new AuthorizationException("Invalid JWT Token");
    }
    @PutMapping("/{id}")
    public Employee updateEmployee(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, @PathVariable("id") long id, @RequestBody Employee employee) throws AuthorizationException {
        if(registrationFeign.authorizeTheRequest(requestTokenHeader))
            return employeeService.updateEmployee(id, employee);
        else
            throw new AuthorizationException("Invalid JWT Token");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable("id") long id) {
        return employeeService.deleteEmployee(requestTokenHeader, id);
    }
    @PostMapping
    public ResponseEntity<Map> addEmployee(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, @RequestBody EmployeeDto employeeDto) {
        log.info(requestTokenHeader);
        return employeeService.addEmployee(requestTokenHeader, employeeDto);
    }
}
