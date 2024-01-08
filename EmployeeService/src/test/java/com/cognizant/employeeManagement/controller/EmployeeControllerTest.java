package com.cognizant.employeeManagement.controller;

import com.cognizant.employeeManagement.dto.EmployeeDto;
import com.cognizant.employeeManagement.feign.RegistrationFeign;
import com.cognizant.employeeManagement.model.Employee;
import com.cognizant.employeeManagement.service.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RegistrationFeign registrationFeign;
    @MockBean
    private EmployeeServiceImpl service;
    private String token = "Bearer test_token";
    private String err_token = "test_token";
    private List<Employee> employeeList;
    private Employee emp1,emp2;
    private EmployeeDto employeeDto;
    @BeforeEach
    void setup() {
        emp1 = new Employee(1,"Saibal","Male",22,12000.0);
        emp2 = new Employee(2,"Sagnik","Male",21,25000.0);
        employeeList = new ArrayList<Employee>();
        employeeList.add(emp1);
        employeeList.add(emp2);
        employeeDto = new EmployeeDto("Saibal","saibalmaiti23@gmail.com","Male",21,12000.0);
        when(service.getEmployees()).thenReturn(employeeList);
        when(service.getEmployeeById(1)).thenReturn(emp1);
        when(service.deleteEmployee(token,1)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(service.deleteEmployee(token,2)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        when(service.addEmployee(token,employeeDto)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(service.updateEmployee(1,emp1)).thenReturn(emp1);
        when(registrationFeign.authorizeTheRequest(token)).thenReturn(true);
        when(registrationFeign.authorizeTheRequest(err_token)).thenReturn(false);

    }
    @Test
    void testSuccessfulGetEmployees() throws Exception {
        mockMvc.perform(get("/employee").header("Authorization", token)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }
    @Test
    void testFailedGetEmployees() throws Exception {
        mockMvc.perform(get("/employee").header("Authorization",err_token)).andExpect(status().isBadRequest());
    }
    @Test
    void testSuccessfulGetEmployeeById() throws Exception {
        mockMvc.perform(get("/employee/1").header("Authorization",token)).andExpect(status().isOk());
    }
    @Test
    void testFailedGetEmployeeById() throws Exception {
        mockMvc.perform(get("/employee/1").header("Authorization",err_token)).andExpect(status().isBadRequest());
    }
    @Test
    void testSuccessfulDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/employee/1").header("Authorization",token)).andExpect(status().isOk());
    }
    @Test
    void testFailedDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/employee/2").header("Authorization",token)).andExpect(status().isNotFound());
    }
    @Test
    void testSuccessfulAddEmployee() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/employee").header("Authorization",token).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(employeeDto))).andExpect(status().isOk());
    }
    @Test
    void testSuccessfulUpdateEmployee() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(put("/employee/1").header("Authorization",token).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(emp1))).andExpect(status().isOk());
    }
    @Test
    void testFailedUpdateEmployee() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(put("/employee/1").header("Authorization",err_token).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(emp1))).andExpect(status().isBadRequest());
    }

}
