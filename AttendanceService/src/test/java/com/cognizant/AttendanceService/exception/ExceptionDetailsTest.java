package com.cognizant.AttendanceService.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ExceptionDetailsTest {
    private ExceptionDetails details = new ExceptionDetails(LocalDateTime.now(), HttpStatus.ACCEPTED,"message");

    @Test
    void testMessageSetter() {
        details.setMessage("new message");
        assertThat(details.getMessage().equals("new message"));

    }
    @Test
    void testStatusSetter() {
        details.setStatus(HttpStatus.BAD_REQUEST);
        assertThat(details.getStatus().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    void testTimeStampSetter() {

        LocalDateTime date = LocalDateTime.now();
        details.setTimeStamp(date);
        assertThat(details.getTimeStamp().equals(date));

    }
}
