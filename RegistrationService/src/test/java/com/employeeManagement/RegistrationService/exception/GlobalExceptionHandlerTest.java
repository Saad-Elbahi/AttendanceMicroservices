package com.employeeManagement.RegistrationService.exception;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.MissingRequestHeaderException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GlobalExceptionHandlerTest {
    @InjectMocks
    GlobalExceptionHandler globalExceptionHandler;
    @Mock
    private MissingRequestHeaderException missingRequestHeaderException;

    @Test
    void handleAuthorizationExceptionTest() {
        AuthorizationException e = new AuthorizationException("message");
        assertEquals(400, globalExceptionHandler.handelAuthorizationException(e).getStatusCodeValue());
    }

    @Test
    void handlesMissingRequestHeaderExceptionTest() {
        when(missingRequestHeaderException.getMessage()).thenReturn("test_message");
        assertEquals(400, globalExceptionHandler.handleMissingRequestHeaderException(missingRequestHeaderException).getStatusCodeValue());
    }
    @Test
    void handelIllegalStateExceptionTest() {
        IllegalStateException e = new IllegalStateException("test_message");
        assertEquals(400, globalExceptionHandler.handelIllegalStateException(e).getStatusCodeValue());
    }
}
