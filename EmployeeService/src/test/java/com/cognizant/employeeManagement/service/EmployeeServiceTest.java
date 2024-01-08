package com.cognizant.employeeManagement.service;

import com.cognizant.employeeManagement.dao.EmployeeRepository;
import com.cognizant.employeeManagement.dto.EmployeeDto;
import com.cognizant.employeeManagement.dto.UserDto;
import com.cognizant.employeeManagement.feign.RegistrationFeign;
import com.cognizant.employeeManagement.model.Employee;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository repository;
    @Mock
    private RegistrationFeign registrationFeign;
    @InjectMocks
    private EmployeeServiceImpl service;
    private List<Employee> employeeList;
    private String token;
    private UserDto userDto;
    private Employee emp1,emp2;

    @BeforeEach
    void setup() {
        emp1 = new Employee(1,"Saibal","Male",22,12000.0);
        emp2 = new Employee(2,"Sagnik","Male",21,25000.0);
        employeeList = new ArrayList<Employee>();
        employeeList.add(emp1);
        employeeList.add(emp2);
        userDto = new UserDto(1,"saibalmaiti23@gmail.com","Password00$","ROLE_USER");
        token = "Bearer testToken";
        when(repository.findAll()).thenReturn(employeeList);
        when(repository.findById((long)1)).thenReturn(Optional.of(emp1));
        when(repository.findById((long)3)).thenReturn(Optional.empty());
    }
    @Test
    void testGetEmployees() {
        assertEquals(2,service.getEmployees().size());
    }
    @Test
    void testSuccessfulRegistration() {
        EmployeeDto employeeDto = new EmployeeDto("Saibal","saibalmaiti23@gmail.com","Male",21,12000.0);
        when(registrationFeign.registerUser(token,userDto)).thenReturn(ResponseEntity.ok("Test Response"));
        assertEquals(200,service.addEmployee(token,employeeDto).getStatusCodeValue());
    }
    @Test
    void testSuccessfulDelete() {
        when(registrationFeign.deleteUser(token,1)).thenReturn(ResponseEntity.ok("User Deleted Successfully"));
        assertEquals(200,service.deleteEmployee(token,1).getStatusCodeValue());
    }
    @Test
    void testFailedDelete() {
        assertEquals(404,service.deleteEmployee(token,3).getStatusCodeValue());
    }
    @Test
    void testSuccessfulUpdate() {
        assertEquals(emp1,service.updateEmployee(1,emp1));
    }
    @Test
    void testFailedUpdate() {
        assertThrows(RuntimeException.class,()->service.updateEmployee(3,emp2));
    }
    @Test
    void testGetEmployeeById() {
        assertEquals(emp1,service.getEmployeeById(1));
    }

}
