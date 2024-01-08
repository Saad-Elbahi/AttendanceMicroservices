package com.employeeManagement.RegistrationService.exception;

import com.employeeManagement.RegistrationService.model.User;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class AuthorizationExceptionTest {
    private AuthorizationException e = new AuthorizationException("message");
    @Test
    void testMessageSetter() {
        assertThat(e).isNotNull();
    }
}
