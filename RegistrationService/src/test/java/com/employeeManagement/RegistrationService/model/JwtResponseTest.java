package com.employeeManagement.RegistrationService.model;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtResponseTest {
    @Test
    void testJwtResponseBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(JwtResponse.class);
    }
}
