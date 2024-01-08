package com.employeeManagement.RegistrationService.dto;

import com.employeeManagement.RegistrationService.model.User;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserResponseTest {
    @Test
    void testUserResponse() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(UserResponse.class);
    }
}
