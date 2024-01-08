package com.cognizant.employeeManagement.model;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeTest {
    @Test
    void testEmployeeBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(Employee.class);
    }
}
