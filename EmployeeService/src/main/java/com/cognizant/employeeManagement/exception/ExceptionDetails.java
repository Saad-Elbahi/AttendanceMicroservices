package com.cognizant.employeeManagement.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionDetails {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timeStamp;
    private HttpStatus status;
    private String message;

    public ExceptionDetails(LocalDateTime timeStamp, HttpStatus status, String message) {
        super();
        this.timeStamp = timeStamp;
        this.status = status;
        this.message = message;
    }
}
