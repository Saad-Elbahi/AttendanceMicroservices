package com.cognizant.employeeManagement.service;

import com.cognizant.employeeManagement.dao.EmployeeRepository;
import com.cognizant.employeeManagement.dto.EmployeeDto;
import com.cognizant.employeeManagement.dto.UserDto;
import com.cognizant.employeeManagement.feign.RegistrationFeign;
import com.cognizant.employeeManagement.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RegistrationFeign registrationFeign;

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public ResponseEntity<Map> addEmployee(String requestTokenHeader,EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setGender(employeeDto.getGender());
        employee.setAge(employeeDto.getAge());
        employee.setSalary(employeeDto.getSalary());

        employeeRepository.save(employee);

        //create User object
        UserDto user = new UserDto();
        user.setUserid(employee.getUserid());
        user.setEmail(employeeDto.getEmail());
        user.setPassword("Password00$");
        user.setRole("ROLE_USER");
        log.info("-----User Object Log------");
        log.info(user.toString());
        try {
            registrationFeign.registerUser(requestTokenHeader,user);
            log.info("Employee added Successfully");
            Map<String, String> response = new HashMap<>();
            response.put("response","Employee added Successfully");
            return ResponseEntity.ok().body(response);
        }
        catch (Exception e) {
            log.info("-----Error While Executiing Feign Client to register User-----");
            employeeRepository.deleteById(employee.getUserid());
            Map<String, String> response = new HashMap<>();
            response.put("response","Failed to add employee");
            return ResponseEntity.status(403).body(response);
        }
    }
    @Override
    public ResponseEntity<?> deleteEmployee(String requestTokenHeader, long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()) {
            try {
                registrationFeign.deleteUser(requestTokenHeader,id);
                employeeRepository.deleteById(id);
                log.info("Employee Deleted Successfully");
                return ResponseEntity.ok(optionalEmployee.get());
            }
            catch (Exception e) {
                log.info("-----Error While Executiing Feign Client to delete User-----");
                return ResponseEntity.status(403).body("Invalid Credentials");
            }
        }
        else {
            log.info("-----Employee with id "+ id + "doesn't exists-----" );
            return ResponseEntity.status(404).body("Failed to delete employee");
        }
    }
    @Override
    public Employee updateEmployee(long id, Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(!optionalEmployee.isPresent()) {
            throw new RuntimeException("Employee with id " + id + "doesn't exists");
        }
        Employee emp = optionalEmployee.get();
        if(!employee.getName().equals("")) {
            emp.setName(employee.getName());
        }
        if(employee.getAge()!= 0) {
            emp.setAge(employee.getAge());
        }
        if(!employee.getGender().equals("")) {
            emp.setGender(employee.getGender());
        }
        if(employee.getSalary() != 0) {
            emp.setSalary(employee.getSalary());
        }
        employeeRepository.save(emp);
        return emp;
    }

    @Override
    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id).get();
    }
}
