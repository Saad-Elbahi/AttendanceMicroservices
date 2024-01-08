package com.cognizant.employeeManagement.dto;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeDtoTest {
    @Test
    void testEmployeeDtoBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(EmployeeDto.class);
    }
}
